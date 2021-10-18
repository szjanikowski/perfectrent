package com.itlibrium.perfectrent.search.adaptations;

import io.vavr.control.Try;

//PORT
public interface AdaptationsConfigProvider {
    Try<AdaptationsConfig> getAdaptationsConfig();
}