package com.coremedia.labs.plugins.adapters.pixabay.model;

import com.coremedia.contenthub.api.ContentHubBlob;
import com.coremedia.contenthub.api.ContentHubObject;
import com.coremedia.contenthub.api.UrlBlobBuilder;
import com.coremedia.mimetype.MimeTypeService;
import jakarta.activation.MimeType;
import jakarta.activation.MimeTypeParseException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Optional;

/**
 * Wrapper for custom blob handling.
 * Needed, because Pixabay API sometimes does not expose the correct content-type header,
 * so we need to react on that and in case the wrapped {@link com.coremedia.contenthub.api.ContentHubDefaultBlob}s
 * mime-type is <code>binary/octet-stream</code> we're trying to guess the correct mime-type by the file name.
 */
public class PixabayContentHubBlob implements ContentHubBlob {

  private final Logger LOG = LoggerFactory.getLogger(PixabayContentHubBlob.class);

  private PixabayItem owner;
  private String classifier;
  private String url;
  private Optional<ContentHubBlob> delegate;

  private MimeTypeService mimeTypeService;

  public PixabayContentHubBlob(PixabayItem owner, String classifier, String url, MimeTypeService mimeTypeService) {
    this.owner = owner;
    this.classifier = classifier;
    this.url = url;
    this.delegate = Optional.ofNullable(new UrlBlobBuilder(owner, classifier).withUrl(url).build());
    this.mimeTypeService = mimeTypeService;
  }

  @Override
  public ContentHubObject getOwner() {
    return owner;
  }

  @Override
  public String getClassifier() {
    return classifier;
  }

  @Override
  public MimeType getContentType() {
    MimeType mimeType = delegate.map(ContentHubBlob::getContentType).orElse(null);

    if (mimeType != null && "binary/octet-stream".equals(mimeType.toString())) {
      // Try to guess mime-type from file name
      String name = FilenameUtils.getName(url);
      try {
        mimeType = new MimeType(mimeTypeService.getMimeTypeForResourceName(name));
      } catch (MimeTypeParseException e) {
        LOG.warn("Unable to guess mime-type for url {}.", url, e);
      }
    }

    return mimeType;
  }

  @Override
  public long getLength() {
    return delegate.map(ContentHubBlob::getLength).orElse(0L);
  }

  @Override
  public InputStream getInputStream() {
    return delegate.map(ContentHubBlob::getInputStream).orElse(null);
  }

  @Override
  public Optional<String> getEtag() {
    return delegate.flatMap(ContentHubBlob::getEtag);
  }

}
