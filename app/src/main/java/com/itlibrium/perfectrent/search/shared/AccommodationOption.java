package com.itlibrium.perfectrent.search.shared;

public record AccommodationOption(String name, Location location, int order) {
    //TODO similarity check to be able to aggregate offers
}
