package com.itlibrium.perfectrent.search.aggregation;

import com.itlibrium.perfectrent.search.shared.AccommodationOffer;
import com.itlibrium.perfectrent.search.shared.AccommodationOption;
import com.itlibrium.perfectrent.search.shared.AggregatedOffers;
import io.vavr.collection.List;
import io.vavr.collection.SortedMap;
import io.vavr.collection.TreeMap;

import java.util.Comparator;
import java.util.function.Predicate;

record OffersBeingAggregated(
        SortedMap<AccommodationOption, List<AccommodationOffer>> accommodationOffersByDetails
) {

    static OffersBeingAggregated create() {
        return new OffersBeingAggregated(TreeMap.empty(Comparator.comparing(AccommodationOption::order)));
    }

    OffersBeingAggregated aggregateWithCheck(AccommodationOffer offer, Predicate<AccommodationOffer> offerCheck) {
        //if offer check not passed, return the same object
        if (!offerCheck.test(offer)) {
            return this;
        }
        //group offers with similar details in the map
        return aggregate(offer);
    }

    OffersBeingAggregated aggregate(AccommodationOffer offer) {
        final AccommodationOption accommodationOption = offer.accommodationOption();
        final List<AccommodationOffer> updatedOffersForGivenDetails =
            accommodationOffersByDetails.get(accommodationOption) //this is a Vavr Option!
                .map(offers -> offers.append(offer))
                .getOrElse(List.of(offer));
        return new OffersBeingAggregated(
            accommodationOffersByDetails.put(accommodationOption, updatedOffersForGivenDetails));
    }

    AggregatedOffers toAggregatedOffers() {
        return new AggregatedOffers(accommodationOffersByDetails);
    }
}
