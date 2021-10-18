package com.itlibrium.perfectrent.search.shared;

import io.vavr.collection.List;

public interface RiskyAreasConfigurationProvider {

     List<RiskyArea> getRiskyAreas(SearchCriteria searchCriteria);

}
