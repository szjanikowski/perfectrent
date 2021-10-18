package com.itlibrium.perfectrent.search.adaptations;

import com.itlibrium.perfectrent.search.shared.AccommodationProvider;
import lombok.Value;

public record PotentialRestoringHiddenOffers(
    AccommodationProvider provider,
    int topOffersRange
) implements PotentialAdaptation {
    public boolean isApplicableFor(Conditions conditions) {
        return !conditions.isWeekend();
    }
}
