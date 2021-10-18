package com.itlibrium.perfectrent.search.shared;

public record AccommodationOffer(
    AccommodationProvider provider,
    AccommodationOption accommodationOption,
    PriceDetails priceDetails
) {
    public boolean isSimilarTo(AccommodationOffer other) {
        return this.accommodationOption.equals(other.accommodationOption);
    }
}
