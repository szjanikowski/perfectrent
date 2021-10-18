package com.itlibrium.perfectrent.search.aggregation;

import com.itlibrium.perfectrent.search.shared.AccommodationOffer;
import com.itlibrium.perfectrent.search.shared.RiskyArea;
import io.vavr.collection.List;

record RiskyAreaCheck(List<RiskyArea> riskyAreas) {

    public boolean check(AccommodationOffer accommodationOffer) {
        return riskyAreas.exists(riskyArea -> riskyArea.contains(accommodationOffer.accommodationOption().location()));
    }
}
