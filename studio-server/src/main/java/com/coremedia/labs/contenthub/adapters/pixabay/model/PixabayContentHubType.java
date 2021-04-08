package com.coremedia.labs.contenthub.adapters.pixabay.model;

import com.coremedia.contenthub.api.ContentHubType;

public enum PixabayContentHubType {

  FOLDER(new ContentHubType("folder")),
  PHOTO(new ContentHubType("photo")),
  VIDEO(new ContentHubType("video"));

  private ContentHubType type;

  PixabayContentHubType(ContentHubType type) {
    this.type = type;
  }

  public ContentHubType getType() {
    return type;
  }

}
