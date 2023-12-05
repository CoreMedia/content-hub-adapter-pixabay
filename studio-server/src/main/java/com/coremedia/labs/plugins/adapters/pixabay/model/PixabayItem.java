package com.coremedia.labs.plugins.adapters.pixabay.model;

import com.coremedia.labs.plugins.adapters.pixabay.service.model.Entity;
import com.coremedia.contenthub.api.ContentHubBlob;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.ContentHubType;
import com.coremedia.contenthub.api.Item;
import com.coremedia.contenthub.api.UrlBlobBuilder;
import com.coremedia.contenthub.api.preview.DetailsElement;
import com.coremedia.contenthub.api.preview.DetailsSection;
import com.coremedia.mimetype.MimeTypeService;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.coremedia.contenthub.api.ContentHubBlob.THUMBNAIL_BLOB_CLASSIFIER;

public abstract class PixabayItem extends PixabayContentHubObject implements Item {

  protected static final String COPYRIGHT_PREFIX = "pixabay.com";

  private PixabayContentHubType type;

  private MimeTypeService mimeTypeService;

  public PixabayItem(@NonNull ContentHubObjectId objectId, PixabayContentHubType type, MimeTypeService mimeTypeService) {
    super(objectId);
    this.type = type;
    this.mimeTypeService = mimeTypeService;
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
    String blobUrl = THUMBNAIL_BLOB_CLASSIFIER.equals(classifier) ? getThumbnailUrl() : getDataUrl();

    if (StringUtils.isNotBlank(blobUrl)) {
      try {
        //blob = new UrlBlobBuilder(this, classifier).withUrl(blobUrl).build();
        blob = new PixabayContentHubBlob(this, classifier, blobUrl, mimeTypeService);
      } catch (Exception e) {
        throw new IllegalArgumentException("Cannot create blob for " + this, e);
      }
    }

    return blob;
  }

  @Nullable
  @Override
  public ContentHubBlob getThumbnailBlob() {
    return getBlob(THUMBNAIL_BLOB_CLASSIFIER);
  }

}
