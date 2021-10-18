package com.itlibrium.perfectrent.search.aggregation;

import com.itlibrium.perfectrent.search.shared.AccommodationOffer;
import com.itlibrium.perfectrent.search.shared.AggregatedOffers;
import com.itlibrium.perfectrent.search.shared.RiskyArea;
import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AggregationOfOffers {

    private final RiskyAreaCheck riskyAreaCheck;

    public static AggregationOfOffers using(List<RiskyArea> riskyAreas) {
        return new AggregationOfOffers(new RiskyAreaCheck(riskyAreas));
    }

    public AggregatedOffers aggregateOffers(List<AccommodationOffer> accommodationOffers) {
        List<AccommodationOffer> aggregatedOffers = accommodationOffers
                .filter(accommodationOffer -> !riskyAreaCheck.check(accommodationOffer));
        final OffersBeingAggregated offersBeingAggregated = aggregatedOffers.foldLeft(OffersBeingAggregated.create(), OffersBeingAggregated::aggregate);
        return offersBeingAggregated.toAggregatedOffers();
    }

    AggregatedOffers aggregateOffers2(List<AccommodationOffer> accommodationOffers) {
        final OffersBeingAggregated offersBeingAggregated =
            accommodationOffers.foldLeft(OffersBeingAggregated.create(),
            (offersBeingAggregated1, offer) ->
                offersBeingAggregated1.aggregateWithCheck(offer, riskyAreaCheck::check));
        return offersBeingAggregated.toAggregatedOffers();
    }

}
