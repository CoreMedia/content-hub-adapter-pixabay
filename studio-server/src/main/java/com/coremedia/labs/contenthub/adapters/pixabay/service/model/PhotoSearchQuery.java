package com.coremedia.labs.contenthub.adapters.pixabay.service.model;

import java.util.ArrayList;
import java.util.List;

public class PhotoSearchQuery extends SearchQuery {

  private Photo.ImageType imageType = Photo.ImageType.photo;
  private Photo.Orientation orientation = Photo.Orientation.all;
  private List<Photo.Color> colors = new ArrayList<>();


  public static PhotoSearchQuery queryForTerm(String term) {
    PhotoSearchQuery searchQuery = new PhotoSearchQuery();
    searchQuery.term = term;
    return searchQuery;
  }

  public static PhotoSearchQuery queryForId(int id) {
    PhotoSearchQuery searchQuery = new PhotoSearchQuery();
    searchQuery.id = id;
    return searchQuery;
  }

  public static PhotoSearchQuery fromQuery(PhotoSearchQuery originalQuery) {
    PhotoSearchQuery searchQuery = new PhotoSearchQuery();
    searchQuery.term = originalQuery.getTerm();
    searchQuery.id = originalQuery.getId();
    searchQuery.category = originalQuery.getCategory();
    searchQuery.colors = originalQuery.getColors();
    searchQuery.imageType = originalQuery.getImageType();
    searchQuery.orientation = originalQuery.getOrientation();
    searchQuery.isEditorsChoice = originalQuery.isEditorsChoice();
    searchQuery.isSafeSearch = originalQuery.isSafeSearch();
    searchQuery.languageCode = originalQuery.getLanguageCode();
    searchQuery.minWidth = originalQuery.getMinWidth();
    searchQuery.minHeight = originalQuery.getMinHeight();
    searchQuery.order = originalQuery.getOrder();
    return searchQuery;
  }

  public PhotoSearchQuery withTerm(String term) {
    this.term = term;
    this.id = -1;
    return this;
  }

  public PhotoSearchQuery withId(int id) {
    this.id = id;
    this.term = null;
    return this;
  }

  public PhotoSearchQuery withLanguageCode(Entity.LanguageCode languageCode) {
    this.languageCode = languageCode;
    return this;
  }

  public PhotoSearchQuery withCategory(Entity.Category category) {
    this.category = category;
    return this;
  }

  public PhotoSearchQuery withMinWidth(int minWidth) {
    assert minWidth >= 0;
    this.minWidth = minWidth;
    return this;
  }

  public PhotoSearchQuery withMinHeight(int minHeight) {
    assert minHeight >= 0;
    this.minHeight = minHeight;
    return this;
  }

  public PhotoSearchQuery withEditorsChoice(boolean value) {
    this.isEditorsChoice = value;
    return this;
  }

  public PhotoSearchQuery withSafeSearch(boolean value) {
    this.isSafeSearch = value;
    return this;
  }

  public PhotoSearchQuery withOrder(Entity.Order order) {
    this.order = order;
    return this;
  }

  public PhotoSearchQuery withImageType(Photo.ImageType imageType) {
    this.imageType = imageType;
    return this;
  }

  public Photo.ImageType getImageType() {
    return imageType;
  }

  public void setImageType(Photo.ImageType imageType) {
    this.imageType = imageType;
  }

  public PhotoSearchQuery withOrientation(Photo.Orientation orientation) {
    this.orientation = orientation;
    return this;
  }

  public Photo.Orientation getOrientation() {
    return orientation;
  }

  public void setOrientation(Photo.Orientation orientation) {
    this.orientation = orientation;
  }

  public PhotoSearchQuery withColor(Photo.Color color) {
    this.colors.add(color);
    return this;
  }

  public PhotoSearchQuery withColors(List<Photo.Color> colors) {
    this.colors.addAll(colors);
    return this;
  }

  public List<Photo.Color> getColors() {
    return colors;
  }

  public void setColors(List<Photo.Color> colors) {
    this.colors = colors;
  }

}
