package com.itlibrium.perfectrent.search.adaptations;

import com.itlibrium.perfectrent.search.shared.AggregatedOffers;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

//Bean - domain service
@RequiredArgsConstructor
public class AdaptationService {
    private final AdaptationsPolicyFactory adaptationsPolicyFactory;

    public Try<AggregatedOffers> adapt(AggregatedOffers offers) {
        //I have a part of the snapshot (offers), but I need to complete it with objects based on the rest of the snapshot (adaptaions configuration + weekend info)
        return adaptationsPolicyFactory.getApplicableAdaptations().map(adaptations -> {
                //Now the snapshot is complete, I can start the adaptation process
                AdaptationProcess adaptationProcess = AdaptationProcess.using(adaptations);
                return adaptationProcess.adapt(OffersBeingAdapted.from(offers));
            }
        ).map(OffersBeingAdapted::toAggregatedOffers);
    }
}
