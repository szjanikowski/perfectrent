package com.itlibrium.perfectrent.search.shared;

public record RiskyArea(
    Area area,
    String reason
) {
    public boolean contains(Location location) {
        return area.contains(location);
    }
}
