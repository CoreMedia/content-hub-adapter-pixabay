package com.coremedia.labs.plugins.adapters.pixabay.configuration;

import com.coremedia.contenthub.api.ContentHubMimeTypeService;
import com.coremedia.labs.plugins.adapters.pixabay.PixabayContentHubAdapterFactory;
import com.coremedia.labs.plugins.adapters.pixabay.PixabayContentHubSettings;
import com.coremedia.contenthub.api.ContentHubAdapterFactory;
import com.coremedia.mimetype.MimeTypeService;
import com.coremedia.mimetype.MimeTypeServiceConfiguration;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        MimeTypeServiceConfiguration.class,
})
public class PixabayContentHubAdapterConfiguration {

  @Bean
  public ContentHubAdapterFactory<PixabayContentHubSettings> pixabayContentHubAdapterFactory(@NonNull MimeTypeService mimeTypeService) {
    return new PixabayContentHubAdapterFactory(mimeTypeService);
  }

}
