package com.coremedia.labs.plugins.adapters.pixabay.model;

import com.coremedia.labs.plugins.adapters.pixabay.service.model.Entity;
import com.coremedia.contenthub.api.ContentHubBlob;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.ContentHubType;
import com.coremedia.contenthub.api.Item;
import com.coremedia.contenthub.api.UrlBlobBuilder;
import com.coremedia.contenthub.api.preview.DetailsElement;
import com.coremedia.contenthub.api.preview.DetailsSection;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public abstract class PixabayItem extends PixabayContentHubObject implements Item {

  protected static final String COPYRIGHT_PREFIX = "pixabay.com";

  private PixabayContentHubType type;

  public PixabayItem(@NonNull ContentHubObjectId objectId, PixabayContentHubType type) {
    super(objectId);
    this.type = type;
  }

  @Override
  public ContentHubType getContentHubType() {
    return type.getType();
  }

  public String getThumbnailUrl() {
    return null;
  }

  public String getDataUrl() {
    return null;
  }

  public abstract String getCopyright();

  protected DetailsSection getStatsDetailsSection(Entity entity) {
    return  new DetailsSection("stats", List.of(
                    new DetailsElement<>("comments", "" + entity.getNumberOfComments())
            ));
  }

  @Nullable
  @Override
  public ContentHubBlob getBlob(String classifier) {
    ContentHubBlob blob = null;
    String blobUrl = getDataUrl();

    if (StringUtils.isNotBlank(blobUrl)) {
      try {
        blob = new UrlBlobBuilder(this, classifier).withUrl(blobUrl).build();
      } catch (Exception e) {
        throw new IllegalArgumentException("Cannot create blob for " + this, e);
      }
    }

    return blob;
  }

}
