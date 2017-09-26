package com.cdk.skeletonproject.data;

import com.google.android.gms.maps.model.LatLng;

public class SongKickEvent {

    private String type;
    private String ageRestriction;
    private String status;
    private LatLng location;
    private String locationName;
    private String eventUri;
    private float popularity;
    private long id;
    private String displayName;
    private String dateTime;
    private Venue venue;

    public static SongKickEvent initialize(SongKickEventResponse songKickEventResponse) {
        SongKickEvent event = new SongKickEvent();
        event.type = songKickEventResponse.getType();
        event.ageRestriction = songKickEventResponse.getAgeRestriction();
        event.status = songKickEventResponse.getStatus();
        final LocationResponse location = songKickEventResponse.getLocation();
        event.location = new LatLng(location.getLat(), location.getLng());
        event.locationName = songKickEventResponse.getLocation().getCity();
        event.eventUri = songKickEventResponse.getUri();
        event.popularity = songKickEventResponse.getPopularity();
        event.id = songKickEventResponse.getId();
        event.displayName = songKickEventResponse.getDisplayName();
        event.dateTime = songKickEventResponse.getStart().getDatetime();
        event.venue = Venue.initialize(songKickEventResponse.getVenue());
        return event;
    }

    public String getType() {
        return type;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public String getStatus() {
        return status;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getEventUri() {
        return eventUri;
    }

    public float getPopularity() {
        return popularity;
    }

    public long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Venue getVenue() {
        return venue;
    }

//    private LocationResponse location;
//    private List<PerformanceResponse> performances = null;
//    private TimeResponse start;

}
