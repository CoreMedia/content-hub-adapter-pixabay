package com.coremedia.labs.plugins.adapters.pixabay.model;

import com.coremedia.labs.plugins.adapters.pixabay.service.model.Video;
import com.coremedia.contenthub.api.ContentHubBlob;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.UrlBlobBuilder;
import com.coremedia.contenthub.api.preview.DetailsElement;
import com.coremedia.contenthub.api.preview.DetailsSection;
import com.coremedia.mimetype.MimeTypeService;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class PixabayVideoItem extends PixabayItem {

  private Video video;

  public PixabayVideoItem(@NonNull ContentHubObjectId objectId, @NonNull Video video, @NonNull MimeTypeService mimeTypeService) {
    super(objectId, PixabayContentHubType.VIDEO, mimeTypeService);
    this.video = video;
  }

  @Override
  public String getCoreMediaContentType() {
    return "CMVideo";
  }

  @Override
  public String getName() {
    String tags = video.getTags();
    return StringUtils.isNotBlank(tags) ? tags : String.valueOf(getVideo().getId());
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

    Duration videoDuration = Duration.ofSeconds(getVideo().getDuration());

    return List.of(
            // Details
            new DetailsSection("main", List.of(
                    new DetailsElement<>(getName(), false, Objects.requireNonNullElse(blob, SHOW_TYPE_ICON))
            ), false, false, false),

            // Metadata
            new DetailsSection("metadata", List.of(
                    new DetailsElement<>("id", video.getId()),
                    new DetailsElement<>("type", getVideo().getType()),
                    new DetailsElement<>("duration", DurationFormatUtils.formatDuration(videoDuration.toMillis(), "H:mm:ss", true)),
                    new DetailsElement<>("page", video.getPageUrl()),
                    new DetailsElement<>("user", video.getUser())
            )),

            // Stats
            new DetailsSection("stats", List.of(
                    new DetailsElement<>("views", "" + video.getNumberOfViews()),
                    new DetailsElement<>("downloads", "" + video.getNumberOfDownloads()),
                    new DetailsElement<>("favorites", "" + video.getNumberOfFavorites()),
                    new DetailsElement<>("likes", "" + video.getNumberOfLikes()),
                    new DetailsElement<>("comments", "" + video.getNumberOfComments())
            ), true, true, true)
    );
  }

  @Override
  public String getThumbnailUrl() {
    return String.format("https://i.vimeocdn.com/video/%s_640x360.jpg", video.getPictureId());
  }

  @Override
  public String getDataUrl() {
    return video.getVideos().getLarge().getUrl();
  }

  @Override
  public String getCopyright() {
    return video.getUser();
  }

  public Video getVideo() {
    return video;
  }

}
