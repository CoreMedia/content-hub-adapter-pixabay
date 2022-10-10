package com.coremedia.labs.plugins.adapters.pixabay;

import com.coremedia.contenthub.api.ContentHubAdapter;
import com.coremedia.contenthub.api.ContentHubAdapterFactory;
import com.coremedia.mimetype.MimeTypeService;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;

@DefaultAnnotation(NonNull.class)
public class PixabayContentHubAdapterFactory implements ContentHubAdapterFactory<PixabayContentHubSettings> {

  private static final String ADAPTER_ID = "pixabay";

  private MimeTypeService mimeTypeService;

  public PixabayContentHubAdapterFactory(MimeTypeService mimeTypeService) {
    this.mimeTypeService = mimeTypeService;
  }

  @Override
  public String getId() {
    return ADAPTER_ID;
  }

  @Override
  public ContentHubAdapter createAdapter(@NonNull PixabayContentHubSettings settings,
                                         @NonNull String connectionId) {
    return new PixabayContentHubAdapter(settings, connectionId, mimeTypeService);
  }
}
