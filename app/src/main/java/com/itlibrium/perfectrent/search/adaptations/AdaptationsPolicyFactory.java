package com.itlibrium.perfectrent.search.adaptations;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

//Bean - domain service
@RequiredArgsConstructor
class AdaptationsPolicyFactory {

    private final AdaptationsConfigProvider adaptaionsConfigProvider;

    private final TimeInfoProvider timeInfoProvider;

    Try<Adaptations> getApplicableAdaptations() {
        boolean isWeekend = timeInfoProvider.isWeekend();
        return adaptaionsConfigProvider.getAdaptationsConfig().map(config -> {
            Conditions conditions = Conditions.builder().isWeekend(isWeekend).build();
            return new Adaptations(config.potentialAdaptations()
                    .filter(potentialAdaptation -> potentialAdaptation.isApplicableFor(conditions))
                    .map(this::mapPotentialAdaptationToApplicable)
                    .toList());
        });
    }

    ApplicableAdaptation mapPotentialAdaptationToApplicable(PotentialAdaptation potentialAdaptation) {
        //pattern matching
        return switch (potentialAdaptation) {
            case PotentialOutliersRemoval potentialOutliersRemoval -> OutliersRemoval.from(potentialOutliersRemoval);
            case PotentialRestoringHiddenOffers potentialRestoringHiddenOffers -> RestoringHiddenOffers.from(potentialRestoringHiddenOffers);
            default -> throw new IllegalArgumentException("Unknown adaptation type in the configuration");
        };
    }

}
