package com.itlibrium.perfectrent.search.shared;

import lombok.Value;

import java.math.BigDecimal;

public record PriceDetails(
    int price,
    int currency,
    int decimalPlaces
) {
    public BigDecimal toBigDecimal() {
        return new BigDecimal(price).movePointLeft(decimalPlaces);
    }
}
