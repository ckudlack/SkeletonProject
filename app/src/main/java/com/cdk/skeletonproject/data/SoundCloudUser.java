package com.cdk.skeletonproject.data;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class SoundCloudUser implements RealmModel {

    @PrimaryKey
    private int id;

    /* Added from SK */
    private long songKickId;

    private String kind;
    private String permalink;
    private String username;
    private String lastModified;
    private String uri;
    private String permalinkUrl;
    private String avatarUrl;
    private String country;
    private String firstName;
    private String lastName;
    private String fullName;
    private String description;
    private String city;
    private String website;
    private String websiteTitle;
    private int trackCount;
    private int playlistCount;
    private boolean online;
    private String plan;
    private int publicFavoritesCount;
    private int followersCount;
    private int followingsCount;
    private int repostsCount;
    private RealmList<SoundCloudUser> followings;
    private boolean isDefaultUser = false;
    private boolean userIsSelected = true;

    public static SoundCloudUser initialize(SoundCloudUserResponse soundCloudUserResponse) {
        SoundCloudUser soundCloudUser = new SoundCloudUser();
        soundCloudUser.id = soundCloudUserResponse.getId();
        soundCloudUser.username = soundCloudUserResponse.getUsername();
        soundCloudUser.avatarUrl = soundCloudUserResponse.getAvatarUrl();
        return soundCloudUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsiteTitle() {
        return websiteTitle;
    }

    public void setWebsiteTitle(String websiteTitle) {
        this.websiteTitle = websiteTitle;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public int getPlaylistCount() {
        return playlistCount;
    }

    public void setPlaylistCount(int playlistCount) {
        this.playlistCount = playlistCount;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public int getPublicFavoritesCount() {
        return publicFavoritesCount;
    }

    public void setPublicFavoritesCount(int publicFavoritesCount) {
        this.publicFavoritesCount = publicFavoritesCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public void setFollowingsCount(int followingsCount) {
        this.followingsCount = followingsCount;
    }

    public int getRepostsCount() {
        return repostsCount;
    }

    public void setRepostsCount(int repostsCount) {
        this.repostsCount = repostsCount;
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
}
