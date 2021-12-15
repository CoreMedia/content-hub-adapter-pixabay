package com.coremedia.labs.plugins.adapters.pixabay.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Photo extends Entity {

  public enum ImageType {
    all, photo, illustration, vector
  }

  public enum Orientation {
    all, horizontal, vertical
  }

  public enum Color {
    grayscale, transparent, red, orange, yellow, green, turquoise, blue, lilac, pink, white, gray, black, brown
  }

  @JsonProperty("type")
  private String type;

  @JsonProperty("previewURL")
  private String previewUrl;

  @JsonProperty("previewWidth")
  private int previewWidth;

  @JsonProperty("previewHeight")
  private int previewHeight;

  @JsonProperty("webformatURL")
  private String webformatUrl;

  @JsonProperty("webformatWidth")
  private int webformatWidth;

  @JsonProperty("webformatHeight")
  private int webformatHeight;

  @JsonProperty("largeImageURL")
  private String largeImageUrl;

  @JsonProperty("imageWidth")
  private int imageWidth;

  @JsonProperty("imageHeight")
  private int imageHeight;

  @JsonProperty("imageSize")
  private int imageSize;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPreviewUrl() {
    return previewUrl;
  }

  public void setPreviewUrl(String previewUrl) {
    this.previewUrl = previewUrl;
  }

  public int getPreviewWidth() {
    return previewWidth;
  }

  public void setPreviewWidth(int previewWidth) {
    this.previewWidth = previewWidth;
  }

  public int getPreviewHeight() {
    return previewHeight;
  }

  public void setPreviewHeight(int previewHeight) {
    this.previewHeight = previewHeight;
  }

  public String getWebformatUrl() {
    return webformatUrl;
  }

  public void setWebformatUrl(String webformatUrl) {
    this.webformatUrl = webformatUrl;
  }

  public int getWebformatWidth() {
    return webformatWidth;
  }

  public void setWebformatWidth(int webformatWidth) {
    this.webformatWidth = webformatWidth;
  }

  public int getWebformatHeight() {
    return webformatHeight;
  }

  public void setWebformatHeight(int webformatHeight) {
    this.webformatHeight = webformatHeight;
  }

  public String getLargeImageUrl() {
    return largeImageUrl;
  }

  public void setLargeImageUrl(String largeImageUrl) {
    this.largeImageUrl = largeImageUrl;
  }

  public int getImageWidth() {
    return imageWidth;
  }

  public void setImageWidth(int imageWidth) {
    this.imageWidth = imageWidth;
  }

  public int getImageHeight() {
    return imageHeight;
  }

  public void setImageHeight(int imageHeight) {
    this.imageHeight = imageHeight;
  }

  public int getImageSize() {
    return imageSize;
  }

  public void setImageSize(int imageSize) {
    this.imageSize = imageSize;
  }

}
