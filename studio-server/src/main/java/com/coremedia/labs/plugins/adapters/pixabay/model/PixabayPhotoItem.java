package com.coremedia.labs.plugins.adapters.pixabay.model;

import com.coremedia.labs.plugins.adapters.pixabay.service.model.Photo;
import com.coremedia.contenthub.api.ContentHubBlob;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.UrlBlobBuilder;
import com.coremedia.contenthub.api.preview.DetailsElement;
import com.coremedia.contenthub.api.preview.DetailsSection;
import com.coremedia.mimetype.MimeTypeService;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

public class PixabayPhotoItem extends PixabayItem {

  private Photo photo;

  public PixabayPhotoItem(@NonNull ContentHubObjectId objectId, @NonNull Photo photo, @NonNull MimeTypeService mimeTypeService) {
    super(objectId, PixabayContentHubType.PHOTO, mimeTypeService);
    this.photo = photo;
  }

  @Override
  public String getCoreMediaContentType() {
    return "CMPicture";
  }

  @Override
  public String getName() {
    String pageUrl = photo.getPageUrl().replace("https://pixabay.com/photos/", "").replace("/", "");
    return StringUtils.isNotBlank(pageUrl) ? pageUrl : String.valueOf(getPhoto().getId());
  }

  @Nullable
  @Override
  public ContentHubBlob getThumbnailBlob() {
    return getBlob(ContentHubBlob.THUMBNAIL_BLOB_CLASSIFIER);
  }

  @Nullable
  @Override
  public String getDescription() {
    return "";
  }

  @NonNull
  @Override
  public List<DetailsSection> getDetails() {
    ContentHubBlob blob = null;
    String thumbnailUrl = getThumbnailUrl();
    if (StringUtils.isNotBlank(thumbnailUrl)) {
      blob = new UrlBlobBuilder(this, "preview").withUrl(thumbnailUrl).build();
    }

    return List.of(
            // Details
            new DetailsSection("main", List.of(
                    new DetailsElement<>(getName(), false, Objects.requireNonNullElse(blob, SHOW_TYPE_ICON))
            ), false, false, false),

            // Metadata
            new DetailsSection("metadata", List.of(
                    new DetailsElement<>("id", photo.getId()),
                    new DetailsElement<>("dimensions", String.format("%dx%d", photo.getImageWidth(), photo.getImageHeight())),
                    new DetailsElement<>("size", FileUtils.byteCountToDisplaySize(photo.getImageSize())),
                    new DetailsElement<>("page", photo.getPageUrl()),
                    new DetailsElement<>("user", photo.getUser())
            )),

            // Stats
            new DetailsSection("stats", List.of(
                    new DetailsElement<>("views", "" + photo.getNumberOfViews()),
                    new DetailsElement<>("downloads", "" + photo.getNumberOfDownloads()),
                    new DetailsElement<>("favorites", "" + photo.getNumberOfFavorites()),
                    new DetailsElement<>("likes", "" + photo.getNumberOfLikes()),
                    new DetailsElement<>("comments", "" + photo.getNumberOfComments())
            ), true, true, true)
    );
  }

  @Override
  public String getThumbnailUrl() {
    return getPhoto().getPreviewUrl();
  }

  @Override
  public String getDataUrl() {
    return getPhoto().getLargeImageUrl();
  }

  @Override
  public String getCopyright() {
    return photo.getUser();
  }

  public Photo getPhoto() {
    return photo;
  }

}
