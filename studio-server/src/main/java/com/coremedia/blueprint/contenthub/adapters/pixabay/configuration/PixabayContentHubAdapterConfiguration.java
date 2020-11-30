package com.coremedia.blueprint.contenthub.adapters.pixabay.configuration;

import com.coremedia.blueprint.contenthub.adapters.pixabay.PixabayContentHubAdapterFactory;
import com.coremedia.blueprint.contenthub.adapters.pixabay.PixabayContentHubSettings;
import com.coremedia.contenthub.api.ContentHubAdapterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PixabayContentHubAdapterConfiguration {

  @Bean
  public ContentHubAdapterFactory<PixabayContentHubSettings> pixabayContentHubAdapterFactory() {
    return new PixabayContentHubAdapterFactory();
  }

}
