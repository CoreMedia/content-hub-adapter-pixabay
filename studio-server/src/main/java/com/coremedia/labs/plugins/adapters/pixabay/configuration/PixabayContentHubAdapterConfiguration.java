package com.coremedia.labs.plugins.adapters.pixabay.configuration;

import com.coremedia.labs.plugins.adapters.pixabay.PixabayContentHubAdapterFactory;
import com.coremedia.labs.plugins.adapters.pixabay.PixabayContentHubSettings;
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
