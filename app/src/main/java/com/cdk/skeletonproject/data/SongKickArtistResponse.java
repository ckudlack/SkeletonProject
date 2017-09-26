package com.cdk.skeletonproject.data;

import java.util.List;

public class SongKickArtistResponse {

    public String displayName;
    public List<SongKickIdentifier> identifiers = null;
    public String uri;
    public int id;
    public String onTourUntil;

    public String getDisplayName() {
        return displayName;
    }

    public List<SongKickIdentifier> getIdentifiers() {
        return identifiers;
    }

    public String getUri() {
        return uri;
    }

    public int getId() {
        return id;
    }

    public String getOnTourUntil() {
        return onTourUntil;
    }
}
