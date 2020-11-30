package com.coremedia.blueprint.contenthub.adapters.pixabay.service.model;

public class VideoSearchQuery extends SearchQuery {

  private Video.VideoType videoType = Video.VideoType.all;

  public static VideoSearchQuery queryForTerm(String term) {
    VideoSearchQuery searchQuery = new VideoSearchQuery();
    searchQuery.term = term;
    return searchQuery;
  }

  public static VideoSearchQuery queryForId(int id) {
    VideoSearchQuery searchQuery = new VideoSearchQuery();
    searchQuery.id = id;
    return searchQuery;
  }

  public static VideoSearchQuery fromQuery(VideoSearchQuery originalQuery) {
    VideoSearchQuery searchQuery = new VideoSearchQuery();
    searchQuery.term = originalQuery.getTerm();
    searchQuery.id = originalQuery.getId();
    searchQuery.category = originalQuery.getCategory();
    searchQuery.videoType = originalQuery.getVideoType();
    searchQuery.isEditorsChoice = originalQuery.isEditorsChoice();
    searchQuery.isSafeSearch = originalQuery.isSafeSearch();
    searchQuery.languageCode = originalQuery.getLanguageCode();
    searchQuery.minWidth = originalQuery.getMinWidth();
    searchQuery.minHeight = originalQuery.getMinHeight();
    searchQuery.order = originalQuery.getOrder();
    return searchQuery;
  }

  public VideoSearchQuery withTerm(String term) {
    this.term = term;
    this.id = -1;
    return this;
  }

  public VideoSearchQuery withId(int id) {
    this.id = id;
    this.term = null;
    return this;
  }

  public VideoSearchQuery withLanguageCode(Entity.LanguageCode languageCode) {
    this.languageCode = languageCode;
    return this;
  }

  public VideoSearchQuery withCategory(Entity.Category category) {
    this.category = category;
    return this;
  }

  public VideoSearchQuery withMinWidth(int minWidth) {
    assert minWidth >= 0;
    this.minWidth = minWidth;
    return this;
  }

  public VideoSearchQuery withMinHeight(int minHeight) {
    assert minHeight >= 0;
    this.minHeight = minHeight;
    return this;
  }

  public VideoSearchQuery withEditorsChoice(boolean value) {
    this.isEditorsChoice = value;
    return this;
  }

  public VideoSearchQuery withSafeSearch(boolean value) {
    this.isSafeSearch = value;
    return this;
  }

  public VideoSearchQuery withOrder(Entity.Order order) {
    this.order = order;
    return this;
  }

  public VideoSearchQuery withVideoType(Video.VideoType videoType) {
    this.videoType = videoType;
    return this;
  }

  public Video.VideoType getVideoType() {
    return videoType;
  }

  public void setVideoType(Video.VideoType videoType) {
    this.videoType = videoType;
  }

}
