package com.cdk.skeletonproject.data;

public class SongKickArtist {

    public String displayName;
    public String uri;
    public int id;
    public String onTourUntil;

    public static SongKickArtist initialize(SongKickArtistResponse songKickArtistResponse) {
        SongKickArtist songKickArtist = new SongKickArtist();
        songKickArtist.displayName = songKickArtistResponse.getDisplayName();
        songKickArtist.uri = songKickArtistResponse.getUri();
        songKickArtist.id = songKickArtistResponse.getId();
        songKickArtist.onTourUntil = songKickArtistResponse.getOnTourUntil();
        return songKickArtist;
    }

    public String getDisplayName() {
        return displayName;
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
