package com.coremedia.labs.contenthub.adapters.pixabay.model;

import com.coremedia.labs.contenthub.adapters.pixabay.service.model.SearchQuery;
import com.coremedia.contenthub.api.ContentHubObjectId;
import edu.umd.cs.findbugs.annotations.NonNull;

public class PixabaySearchFolder extends PixabayFolder {

  private SearchQuery query;

  public PixabaySearchFolder(@NonNull ContentHubObjectId objectId, String name, SearchQuery query, PixabayContentHubType type) {
    super(objectId, name, type);
    this.query = query;
  }

  public SearchQuery getQuery() {
    return query;
  }

  public void setQuery(SearchQuery query) {
    this.query = query;
  }

}
