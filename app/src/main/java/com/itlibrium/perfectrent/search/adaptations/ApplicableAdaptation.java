package com.itlibrium.perfectrent.search.adaptations;

import com.itlibrium.perfectrent.search.shared.AccommodationProvider;
import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.Value;

import java.math.BigDecimal;
import java.util.function.Function;

interface ApplicableAdaptation {
    OffersBeingAdapted apply(OffersBeingAdapted offers);
}

@Value
class OutliersRemoval implements ApplicableAdaptation {

    AccommodationProvider provider;
    int filteringThresholdInPercent;

    static OutliersRemoval from(PotentialOutliersRemoval potentialOutliersRemoval) {
        return new OutliersRemoval(potentialOutliersRemoval.provider(), potentialOutliersRemoval.filteringThresholdInPercent());
    }

    @Override
    public OffersBeingAdapted apply(OffersBeingAdapted offersBeingAdapted) {
        return new OffersBeingAdapted(offersBeingAdapted.getAdjustedOffers().mapValues(this::removeOutliersFromOffersForOption));
    }

    private List<AdaptableOffer> removeOutliersFromOffersForOption(List<AdaptableOffer> offers) {
        return offers.map(offer -> {
            if (offer.getOffer().provider().equals(provider)) {
                Option<BigDecimal> optionalMaxPriceOfOtherOffers = offers
                    .filter(o -> !o.equals(offer))
                    .filter(o -> !o.isHidden()) //should we consider only visible offers - or all of them?
                    .map(o -> o.getOffer().priceDetails().toBigDecimal())
                    .max();
                if(optionalMaxPriceOfOtherOffers.isEmpty()) {
                    //it means there are no other offers so we don't want to hide this one
                    return offer;
                }
                //if the price is more than filteringThresholdInPercent% higher than the highest price
                // we want to hide the offer
                final BigDecimal offerPrice = offer.getOffer().priceDetails().toBigDecimal();
                final BigDecimal maxPriceOfOtherOffers = optionalMaxPriceOfOtherOffers.get();
                final BigDecimal thresholdFactor = BigDecimal.valueOf(1 + filteringThresholdInPercent / 100.0);
                if(offerPrice.compareTo(maxPriceOfOtherOffers.multiply(thresholdFactor)) > 0) {
                    return offer.hide();
                }
            }
            return offer;
        });
    }
}


@Value
class RestoringHiddenOffers implements ApplicableAdaptation {

    AccommodationProvider provider;
    int topOffersRange;

    public static ApplicableAdaptation from(PotentialRestoringHiddenOffers potentialRestoringHiddenOffers) {
        return new RestoringHiddenOffers(
            potentialRestoringHiddenOffers.provider(),
            potentialRestoringHiddenOffers.topOffersRange()
        );
    }

    @Override
    public OffersBeingAdapted apply(OffersBeingAdapted offersBeingAdapted) {
        //get given provider's offers from the first topOffersRange offers of the map
        List<AdaptableOffer> topOffers = offersBeingAdapted.getAdjustedOffers()
            .values()
            .take(topOffersRange)
            .flatMap(Function.identity())
            .filter(offer -> offer.getOffer().provider().equals(provider))
            .toList();
        //check if all the offers are hidden
        boolean allHidden = topOffers.forAll(AdaptableOffer::isHidden);
        if(allHidden && topOffers.nonEmpty()) {
            //if all the offers are hidden, restore the first one from the topOffers list
            AdaptableOffer firstHiddenOffer = topOffers.head();
            return new OffersBeingAdapted(offersBeingAdapted.getAdjustedOffers()
                .put(firstHiddenOffer.getOffer().accommodationOption(),
                    offersBeingAdapted.getAdjustedOffers().get(firstHiddenOffer.getOffer().accommodationOption())
                        .map(offers -> offers.replace(firstHiddenOffer, firstHiddenOffer.restore()))
                        .getOrElse(List.of(firstHiddenOffer.restore()))));
        }
        return offersBeingAdapted;
    }
}




