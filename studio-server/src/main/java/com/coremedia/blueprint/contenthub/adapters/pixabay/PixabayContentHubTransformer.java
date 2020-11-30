package com.coremedia.blueprint.contenthub.adapters.pixabay;

import com.coremedia.blueprint.contenthub.adapters.pixabay.model.PixabayItem;
import com.coremedia.blueprint.contenthub.adapters.pixabay.model.PixabayPhotoItem;
import com.coremedia.blueprint.contenthub.adapters.pixabay.model.PixabayVideoItem;
import com.coremedia.contenthub.api.ContentHubAdapter;
import com.coremedia.contenthub.api.ContentHubBlob;
import com.coremedia.contenthub.api.ContentHubContentCreationException;
import com.coremedia.contenthub.api.ContentHubContext;
import com.coremedia.contenthub.api.ContentHubObject;
import com.coremedia.contenthub.api.ContentHubTransformer;
import com.coremedia.contenthub.api.ContentModel;
import com.coremedia.contenthub.api.ContentModelReference;
import com.coremedia.contenthub.api.Item;
import com.coremedia.contenthub.api.UrlBlobBuilder;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class PixabayContentHubTransformer implements ContentHubTransformer {

  private static final Logger LOG = LoggerFactory.getLogger(PixabayContentHubTransformer.class);

  @Nullable
  @Override
  public ContentModel transform(Item source, ContentHubAdapter contentHubAdapter, ContentHubContext contentHubContext) throws ContentHubContentCreationException {
    if (!(source instanceof PixabayItem)) {
      throw new IllegalArgumentException("Cannot transform source " + source);
    }

    PixabayItem item = (PixabayItem) source;
    LOG.info("Creating content model for item {}.", item);

    ContentModel contentModel = ContentModel.createContentModel(item);
    contentModel.put("title", item.getName());
    contentModel.put("copyright", item.getCopyright() );

    if (item instanceof PixabayPhotoItem) {
      ContentHubBlob blob = item.getBlob("file");
      if (blob != null) {
        contentModel.put("data", blob);
      }
    } else if (item instanceof PixabayVideoItem) {
      PixabayVideoItem videoItem = (PixabayVideoItem) item;
      contentModel.put("dataUrl", videoItem.getDataUrl());

      // Store image reference
      String thumbnailUrl = videoItem.getThumbnailUrl();
      if (thumbnailUrl != null) {
        ContentModelReference ref = ContentModelReference.create(contentModel, "CMPicture", thumbnailUrl);
        contentModel.put("pictures", Collections.singletonList(ref));
      }
    }

    return contentModel;
  }

  @Nullable
  @Override
  public ContentModel resolveReference(ContentHubObject owner, ContentModelReference reference, ContentHubAdapter contentHubAdapter, ContentHubContext contentHubContext) {
    Object data = reference.getData();
    if (!(data instanceof String)) {
      throw new IllegalArgumentException("Not my reference: " + reference);
    }

    String imageUrl = (String) data;
    String imageName = reference.getOwner().getContentName() + " (Thumbnail)";

    ContentModel referenceModel = ContentModel.createReferenceModel(imageName, reference.getCoreMediaContentType());
    referenceModel.put("data", new UrlBlobBuilder(owner, "thumbnail").withUrl(imageUrl).build());
    referenceModel.put("title", "Video Thumbnail " + imageName);

    return referenceModel;
  }
}
