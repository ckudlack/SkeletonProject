package com.cdk.skeletonproject.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class SoundCloudUser implements RealmModel, Parcelable {

    private int id;
    private String username;
    private String uri;
    private String permalinkUrl;
    private String avatarUrl;
    private String fullName;
    private int followingsCount;
    private RealmList<SoundCloudUser> followings;
    private boolean isDefaultUser = false;
    private boolean userIsSelected = true;

    public static SoundCloudUser initialize(SoundCloudUserResponse soundCloudUserResponse) {
        SoundCloudUser soundCloudUser = new SoundCloudUser();
        soundCloudUser.id = soundCloudUserResponse.getId();
        soundCloudUser.username = soundCloudUserResponse.getUsername();
        soundCloudUser.avatarUrl = soundCloudUserResponse.getAvatarUrl();
        soundCloudUser.uri = soundCloudUserResponse.getUri();
        soundCloudUser.followingsCount = soundCloudUserResponse.getFollowingsCount();
        soundCloudUser.permalinkUrl = soundCloudUserResponse.getPermalinkUrl();
        soundCloudUser.fullName = soundCloudUserResponse.getFullName();
        return soundCloudUser;
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

    public RealmList<SoundCloudUser> getFollowings() {
        return followings;
    }

    public void setFollowings(List<SoundCloudUser> followings) {
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

    public SoundCloudUser() {
    }

    protected SoundCloudUser(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.uri = in.readString();
        this.permalinkUrl = in.readString();
        this.avatarUrl = in.readString();
        this.fullName = in.readString();
        this.followingsCount = in.readInt();
        in.readList(this.followings, SoundCloudUser.class.getClassLoader());
        this.isDefaultUser = in.readByte() != 0;
        this.userIsSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SoundCloudUser> CREATOR = new Parcelable.Creator<SoundCloudUser>() {
        @Override
        public SoundCloudUser createFromParcel(Parcel source) {
            return new SoundCloudUser(source);
        }

        @Override
        public SoundCloudUser[] newArray(int size) {
            return new SoundCloudUser[size];
        }
    };
}
