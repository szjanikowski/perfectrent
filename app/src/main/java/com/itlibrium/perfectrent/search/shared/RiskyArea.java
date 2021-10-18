package com.itlibrium.perfectrent.search.shared;

import lombok.Value;

public record RiskyArea(
    Area area,
    String reason
) {
    public boolean contains(Location location) {
        return area.contains(location);
    }
}
