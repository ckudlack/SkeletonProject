package com.cdk.skeletonproject.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Artist implements RealmModel, Parcelable {

    private int id;
    private String username;
    private String uri;
    private String permalinkUrl;
    private String avatarUrl;
    private String fullName;
    private int followingsCount;
    private RealmList<Artist> followings;
    private RealmList<SongKickEvent> events;
    private boolean isDefaultUser = false;
    private boolean userIsSelected = true;

    public static Artist initialize(SoundCloudUserResponse soundCloudUserResponse) {
        Artist artist = new Artist();
        artist.id = soundCloudUserResponse.getId();
        artist.username = soundCloudUserResponse.getUsername();
        artist.avatarUrl = soundCloudUserResponse.getAvatarUrl();
        artist.uri = soundCloudUserResponse.getUri();
        artist.followingsCount = soundCloudUserResponse.getFollowingsCount();
        artist.permalinkUrl = soundCloudUserResponse.getPermalinkUrl();
        artist.fullName = soundCloudUserResponse.getFullName();
        return artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPermalinkUrl() {
        return permalinkUrl;
    }

    public void setPermalinkUrl(String permalinkUrl) {
        this.permalinkUrl = permalinkUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public void setFollowingsCount(int followingsCount) {
        this.followingsCount = followingsCount;
    }

    public RealmList<Artist> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Artist> followings) {
        this.followings.addAll(followings);
    }

    public void setDefaultUser(boolean defaultUser) {
        isDefaultUser = defaultUser;
    }

    public boolean isDefaultUser() {
        return isDefaultUser;
    }

    public boolean isUserSelected() {
        return userIsSelected;
    }

    public void setUserIsSelected(boolean userIsSelected) {
        this.userIsSelected = userIsSelected;
    }

    public List<SongKickEvent> getEvents() {
        return events;
    }

    public void setEvents(List<SongKickEvent> events) {
        this.events.clear();
        this.events.addAll(events);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.uri);
        dest.writeString(this.permalinkUrl);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.fullName);
        dest.writeInt(this.followingsCount);
        dest.writeByte(this.isDefaultUser ? (byte) 1 : (byte) 0);
        dest.writeByte(this.userIsSelected ? (byte) 1 : (byte) 0);
    }

    public Artist() {
    }

    protected Artist(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.uri = in.readString();
        this.permalinkUrl = in.readString();
        this.avatarUrl = in.readString();
        this.fullName = in.readString();
        this.followingsCount = in.readInt();
        this.isDefaultUser = in.readByte() != 0;
        this.userIsSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}
