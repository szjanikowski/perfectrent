package com.itlibrium.perfectrent.search.adaptations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class AdaptationProcess {
    private final Adaptations adaptations;

    static AdaptationProcess using(Adaptations adaptations) {
        return new AdaptationProcess(adaptations);
    }

    public OffersBeingAdapted adapt(OffersBeingAdapted offers) {
        return adaptations.adaptations()
                .foldLeft(offers, (currentOffers, adaptation) -> adaptation.apply(currentOffers));
    }
}
