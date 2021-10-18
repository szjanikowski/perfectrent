package com.itlibrium.perfectrent.search.shared;

import io.vavr.collection.List;
import io.vavr.collection.SortedMap;

public record AggregatedOffers(
    SortedMap<AccommodationOption, List<AccommodationOffer>> accommodationOffersByDetails) {}
