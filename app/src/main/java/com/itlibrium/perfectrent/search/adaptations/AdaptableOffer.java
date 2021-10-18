package com.itlibrium.perfectrent.search.adaptations;

import com.itlibrium.perfectrent.search.shared.AccommodationOffer;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
class AdaptableOffer {

    AccommodationOffer offer;
    boolean hidden;

    static AdaptableOffer visibleOffer(AccommodationOffer offer) {
        return new AdaptableOffer(offer, false);
    }

    AdaptableOffer hide() {
        return new AdaptableOffer(offer, true);
    }

    public AdaptableOffer restore() {
        return new AdaptableOffer(offer, false);
    }
}
