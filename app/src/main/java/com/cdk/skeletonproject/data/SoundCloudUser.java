package com.cdk.skeletonproject.data;

import com.squareup.moshi.Json;

import java.util.List;

public class SoundCloudUser {

    @Json(name = "id")
    private int id;
    @Json(name = "kind")
    private String kind;
    @Json(name = "permalink")
    private String permalink;
    @Json(name = "username")
    private String username;
    @Json(name = "last_modified")
    private String lastModified;
    @Json(name = "uri")
    private String uri;
    @Json(name = "permalink_url")
    private String permalinkUrl;
    @Json(name = "avatar_url")
    private String avatarUrl;
    @Json(name = "country")
    private String country;
    @Json(name = "first_name")
    private String firstName;
    @Json(name = "last_name")
    private String lastName;
    @Json(name = "full_name")
    private String fullName;
    @Json(name = "description")
    private String description;
    @Json(name = "city")
    private String city;
    @Json(name = "discogs_name")
    private Object discogsName;
    @Json(name = "myspace_name")
    private Object myspaceName;
    @Json(name = "website")
    private String website;
    @Json(name = "website_title")
    private String websiteTitle;
    @Json(name = "track_count")
    private int trackCount;
    @Json(name = "playlist_count")
    private int playlistCount;
    @Json(name = "online")
    private boolean online;
    @Json(name = "plan")
    private String plan;
    @Json(name = "public_favorites_count")
    private int publicFavoritesCount;
    @Json(name = "followers_count")
    private int followersCount;
    @Json(name = "followings_count")
    private int followingsCount;
    @Json(name = "subscriptions")
    private List<Object> subscriptions = null;
    @Json(name = "reposts_count")
    private int repostsCount;

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

    public Object getDiscogsName() {
        return discogsName;
    }

    public void setDiscogsName(Object discogsName) {
        this.discogsName = discogsName;
    }

    public Object getMyspaceName() {
        return myspaceName;
    }

    public void setMyspaceName(Object myspaceName) {
        this.myspaceName = myspaceName;
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

    public List<Object> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Object> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public int getRepostsCount() {
        return repostsCount;
    }

    public void setRepostsCount(int repostsCount) {
        this.repostsCount = repostsCount;
    }
}
