package com.itlibrium.perfectrent.search.adaptations;

import com.itlibrium.perfectrent.search.shared.AccommodationOption;
import com.itlibrium.perfectrent.search.shared.AggregatedOffers;
import io.vavr.collection.List;
import io.vavr.collection.SortedMap;
import lombok.Value;

@Value
class OffersBeingAdapted {

    SortedMap<AccommodationOption, List<AdaptableOffer>> adjustedOffers;

    static OffersBeingAdapted from(AggregatedOffers aggregatedOffers) {
        return new OffersBeingAdapted(aggregatedOffers.accommodationOffersByDetails()
            .mapValues(offers -> offers.map(AdaptableOffer::visibleOffer)));
    }

    AggregatedOffers toAggregatedOffers() {
        return new AggregatedOffers(adjustedOffers.mapValues(offers -> offers.map(AdaptableOffer::getOffer)));
    }
}
