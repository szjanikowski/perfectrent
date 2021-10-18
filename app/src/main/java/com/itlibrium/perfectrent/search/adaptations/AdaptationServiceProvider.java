package com.itlibrium.perfectrent.search.adaptations;

public class AdaptationServiceProvider {

    public static AdaptationService create(AdaptationsConfigProvider adaptationsConfigProvider, TimeInfoProvider timeInfoProvider) {
        AdaptationsPolicyFactory adaptationsPolicyFactory = new AdaptationsPolicyFactory(adaptationsConfigProvider, timeInfoProvider);
        return new AdaptationService(adaptationsPolicyFactory);
    }
}
