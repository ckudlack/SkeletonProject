package com.cdk.skeletonproject.data;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Venue implements RealmModel {

    private Double lat;
    private Double lng;
    private String uri;
    private String displayName;
    private Long id;

    public static Venue initialize(VenueResponse venueResponse) {
        Venue venue = new Venue();
        venue.lat = venueResponse.getLat();
        venue.lng = venueResponse.getLng();
        venue.uri = venueResponse.getUri();
        venue.displayName = venueResponse.getDisplayName();
        venue.id = venueResponse.getId();
        return venue;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public String getUri() {
        return uri;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getId() {
        return id;
    }
}
