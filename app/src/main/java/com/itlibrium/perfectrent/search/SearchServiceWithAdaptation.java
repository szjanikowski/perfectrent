package com.itlibrium.perfectrent.search;


import com.itlibrium.perfectrent.search.adaptations.AdaptationService;
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
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

//Bean - use case - command handling
@RequiredArgsConstructor
public class SearchServiceWithAdaptation {

    private final RetrievalService retrievalService;
    private final RiskyAreasConfigurationProvider riskyAreasConfigurationProvider;
    private final AdaptationService adaptationService;
    private final SearchDtoConverter searchDtoConverter;

    public AggregatedOffersDto searchExceptionBased(SearchCriteriaDto searchCriteriaDto) {
        final SearchCriteria searchCriteria = searchDtoConverter.searchCriteriaFromDto(searchCriteriaDto);
        List<AccommodationOffer> offersFromProviders =  retrievalService.getAccommodationOffersFromProviders(searchCriteria);
        List<RiskyArea> riskyAreas = riskyAreasConfigurationProvider.getRiskyAreas(searchCriteria);
        AggregationOfOffers aggregationOfOffers = AggregationOfOffers.using(riskyAreas);
        AggregatedOffers aggregatedOffers = aggregationOfOffers.aggregateOffers(offersFromProviders);
        //error prone plumbing - we have to carefully pass adaptedOffers to converter!!
        AggregatedOffers adaptedOffers = adaptationService.adapt(aggregatedOffers).get(); //throwing exception from Try if any
        return searchDtoConverter.aggregatedOffersToDto(adaptedOffers);
    }


    public Try<AggregatedOffersDto> searchTryBased(SearchCriteriaDto searchCriteriaDto) {
        return Try.of(() -> {
            final SearchCriteria searchCriteria = searchDtoConverter.searchCriteriaFromDto(searchCriteriaDto);
            List<AccommodationOffer> offersFromProviders =  retrievalService.getAccommodationOffersFromProviders(searchCriteria);
            List<RiskyArea> riskyAreas = riskyAreasConfigurationProvider.getRiskyAreas(searchCriteria);
            AggregationOfOffers aggregationOfOffers = AggregationOfOffers.using(riskyAreas);
            return aggregationOfOffers.aggregateOffers(offersFromProviders);
        })
        .flatMap(adaptationService::adapt)
        .map(searchDtoConverter::aggregatedOffersToDto);
    }
}
