package com.itlibrium.perfectrent.search.adaptations;

import com.itlibrium.perfectrent.search.shared.AccommodationProvider;

public record PotentialOutliersRemoval(
    AccommodationProvider provider,
    int filteringThresholdInPercent
) implements PotentialAdaptation {
    public boolean isApplicableFor(Conditions conditions) {
        return !conditions.isWeekend();
    }
}
