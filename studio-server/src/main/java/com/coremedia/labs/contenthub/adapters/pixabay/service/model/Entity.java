package com.coremedia.labs.contenthub.adapters.pixabay.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Entity {

  @JsonProperty("id")
  private int id;

  @JsonProperty("pageURL")
  private String pageUrl;

  @JsonProperty("tags")
  private String tags;

  @JsonProperty("views")
  private int numberOfViews;

  @JsonProperty("downloads")
  private int numberOfDownloads;

  @JsonProperty("favorites")
  private int numberOfFavorites;

  @JsonProperty("likes")
  private int numberOfLikes;

  @JsonProperty("comments")
  private int numberOfComments;

  @JsonProperty("user_id")
  private int userId;

  @JsonProperty("user")
  private String user;

  @JsonProperty("userImageURL")
  private String userImageUrl;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPageUrl() {
    return pageUrl;
  }

  public void setPageUrl(String pageUrl) {
    this.pageUrl = pageUrl;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public int getNumberOfViews() {
    return numberOfViews;
  }

  public void setNumberOfViews(int numberOfViews) {
    this.numberOfViews = numberOfViews;
  }

  public int getNumberOfDownloads() {
    return numberOfDownloads;
  }

  public void setNumberOfDownloads(int numberOfDownloads) {
    this.numberOfDownloads = numberOfDownloads;
  }

  public int getNumberOfFavorites() {
    return numberOfFavorites;
  }

  public void setNumberOfFavorites(int numberOfFavorites) {
    this.numberOfFavorites = numberOfFavorites;
  }

  public int getNumberOfLikes() {
    return numberOfLikes;
  }

  public void setNumberOfLikes(int numberOfLikes) {
    this.numberOfLikes = numberOfLikes;
  }

  public int getNumberOfComments() {
    return numberOfComments;
  }

  public void setNumberOfComments(int numberOfComments) {
    this.numberOfComments = numberOfComments;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getUserImageUrl() {
    return userImageUrl;
  }

  public void setUserImageUrl(String userImageUrl) {
    this.userImageUrl = userImageUrl;
  }

  public enum LanguageCode {
    cs, da, de, en, es, fr, id, it, hu, nl, no, pl, pt, ro, sk, fi, sv, tr, vi, th, bg, ru, el, ja, ko, zh
  }

  public enum Category {
    backgrounds, fashion, nature, science, education, feelings, health, people, religion, places, animals, industry, computer, food, sports, transportation, travel, buildings, business, music
  }

  public enum Order {
    popular, latest
  }
}
