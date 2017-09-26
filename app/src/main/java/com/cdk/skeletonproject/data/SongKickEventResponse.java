package com.cdk.skeletonproject.data;

import com.squareup.moshi.Json;

import java.util.List;

public class SongKickEventResponse {

    private String type;
    private String ageRestriction;
    private String status;
    @Json(name = "performance")
    private List<PerformanceResponse> performances = null;
    private VenueResponse venue;
    private TimeResponse start;
    private LocationResponse location;
    private String uri;
    private float popularity;
    private long id;
    private String displayName;

    public String getType() {
        return type;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public String getStatus() {
        return status;
    }

    public List<PerformanceResponse> getPerformances() {
        return performances;
    }

    public VenueResponse getVenue() {
        return venue;
    }

    public TimeResponse getStart() {
        return start;
    }

    public LocationResponse getLocation() {
        return location;
    }

    public String getUri() {
        return uri;
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
}
