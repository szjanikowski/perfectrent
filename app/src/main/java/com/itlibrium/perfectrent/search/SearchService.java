package com.itlibrium.perfectrent.search;


import com.itlibrium.perfectrent.search.aggregation.AggregationOfOffers;
import com.itlibrium.perfectrent.search.dto.AggregatedOffersDto;
import com.itlibrium.perfectrent.search.dto.SearchCriteriaDto;
import com.itlibrium.perfectrent.search.retrieval.RetrievalService;
import com.itlibrium.perfectrent.search.shared.AccommodationOffer;
import com.itlibrium.perfectrent.search.shared.AggregatedOffers;
import com.itlibrium.perfectrent.search.shared.RiskyArea;
import com.itlibrium.perfectrent.search.shared.RiskyAreasConfigurationProvider;
import com.itlibrium.perfectrent.search.shared.SearchCriteria;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;

//Bean - use case - command handling
@RequiredArgsConstructor
public class SearchService {

    private final RetrievalService retrievalService;
    private final RiskyAreasConfigurationProvider riskyAreasConfigurationProvider;

    private final SearchDtoConverter searchDtoConverter;

    public AggregatedOffersDto search(SearchCriteriaDto searchCriteriaDto) {
        final SearchCriteria searchCriteria = searchDtoConverter.searchCriteriaFromDto(searchCriteriaDto);
        List<AccommodationOffer> offersFromProviders =  retrievalService.getAccommodationOffersFromProviders(searchCriteria);
        List<RiskyArea> riskyAreas = riskyAreasConfigurationProvider.getRiskyAreas(searchCriteria);
        AggregationOfOffers aggregationOfOffers = AggregationOfOffers.using(riskyAreas);
        AggregatedOffers aggregatedOffers = aggregationOfOffers.aggregateOffers(offersFromProviders);
        return searchDtoConverter.aggregatedOffersToDto(aggregatedOffers);
    }
}
