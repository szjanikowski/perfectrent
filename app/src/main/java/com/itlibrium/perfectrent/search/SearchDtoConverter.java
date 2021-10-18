package com.itlibrium.perfectrent.search;

import com.itlibrium.perfectrent.search.dto.AggregatedOffersDto;
import com.itlibrium.perfectrent.search.dto.SearchCriteriaDto;
import com.itlibrium.perfectrent.search.shared.AggregatedOffers;
import com.itlibrium.perfectrent.search.shared.SearchCriteria;

//Bean
//Could use potentiall automappers like mapstruct
public class SearchDtoConverter {
    public SearchCriteria searchCriteriaFromDto(SearchCriteriaDto searchCriteriaDto) {
        return new SearchCriteria();
    }

    public AggregatedOffersDto aggregatedOffersToDto(AggregatedOffers aggregatedOffers) {
        return new AggregatedOffersDto();
    }
}
