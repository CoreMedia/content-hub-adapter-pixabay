package com.coremedia.labs.contenthub.adapters.pixabay.service;

import com.coremedia.labs.contenthub.adapters.pixabay.service.model.Entity;
import com.coremedia.labs.contenthub.adapters.pixabay.service.model.Photo;
import com.coremedia.labs.contenthub.adapters.pixabay.service.model.PhotoSearchQuery;
import com.coremedia.labs.contenthub.adapters.pixabay.service.model.SearchQuery;
import com.coremedia.labs.contenthub.adapters.pixabay.service.model.Video;
import com.coremedia.labs.contenthub.adapters.pixabay.service.model.SearchResult;
import com.coremedia.labs.contenthub.adapters.pixabay.service.model.VideoSearchQuery;
import com.google.common.base.Joiner;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service class wrapping all API calls to Pixabay API.
 * See <a href="https://pixabay.com/api/docs/">Pixabay API Documentation</a> for details.
 */
@Service
public class PixabayService {

  private static final Logger LOG = LoggerFactory.getLogger(PixabayService.class);

  private static final String DEFAULT_API_ENDPOINT = "https://pixabay.com/api/";

  // Query parameter names
  private static final String KEY = "key";
  private static final String QUERY = "q";
  private static final String LANG = "lang";
  private static final String ID = "id";
  private static final String IMAGE_TYPE = "image_type";
  private static final String VIDEO_TYPE = "video_type";
  private static final String ORIENTATION = "orientation";
  private static final String CATEGORY = "category";
  private static final String MIN_WIDTH = "min_width";
  private static final String MIN_HEIGHT = "min_height";
  private static final String COLORS = "colors";
  private static final String EDITORS_CHOICE = "editors_choice";
  private static final String SAFE_SEARCH = "safesearch";
  private static final String ORDER = "order";
  private static final String PAGE = "page";
  private static final String PER_PAGE = "per_page";

  private static final Entity.LanguageCode DEFAULT_LANG = Entity.LanguageCode.en;
  private static final Entity.Order DEFAULT_ORDER = Entity.Order.popular;
  private static final boolean DEFAULT_SAFE_SEARCH = false;
  private static final int DEFAULT_PAGE = 1;
  private static final int DEFAULT_PER_PAGE = 20;
  private static final int MIN_PER_PAGE = 3;
  private static final int MAX_PER_PAGE = 200;

  private RestTemplate restTemplate;
  private final String apiEndpoint;
  private final String apiKey;

  public PixabayService(@NonNull String apiKey) {
    this(DEFAULT_API_ENDPOINT, apiKey);
  }

  public PixabayService(@NonNull String apiEndpoint, @NonNull String apiKey) {
    this.apiEndpoint = apiEndpoint;
    this.apiKey = apiKey;
    this.restTemplate = new RestTemplate();
  }


  // --- PHOTOS --------------------------------------------------------------------------------------------------------

  private static final Photo.ImageType DEFAULT_IMAGE_TYPE = Photo.ImageType.all;
  private static final Photo.Orientation DEFAULT_ORIENTATION = Photo.Orientation.all;
  private static final int DEFAULT_PHOTO_MIN_WIDTH = 0;
  private static final int DEFAULT_PHOTO_MIN_HEIGHT = 0;

  /**
   * Retrieve a single photo.
   *
   * @param photoId The photo’s ID. Required.
   *
   * @return the {@link Photo} or <code>null</code>
   */
  public Photo getPhotoById(int photoId) {
    ResponseEntity<SearchResult<Photo>> response = performApiCall("",
            null,
            Map.of(ID, photoId),
            new ParameterizedTypeReference<SearchResult<Photo>>() {});
    if (response.getStatusCode().equals(HttpStatus.OK)) {
      return Optional.ofNullable(response.getBody())
              .map(SearchResult::getHits)
              .map(l -> l.get(0))
              .orElse(null);
    } else {
      return null;
    }
  }

  public SearchResult<Photo> searchPhotos(@NonNull String query) {
    return searchPhotos(query, DEFAULT_SAFE_SEARCH, DEFAULT_PAGE, DEFAULT_PER_PAGE, DEFAULT_ORDER);
  }

  public SearchResult<Photo> searchPhotos(@NonNull String query, boolean safeSearch, int page, int perPage, Entity.Order orderBy) {
    if (StringUtils.isBlank(query)) {
      return new SearchResult<>();
    }

    // Replace all whitespace in query with '+'
    query = query.trim().replaceAll("\\s", "+");

    ResponseEntity<SearchResult<Photo>> response = performApiCall("",
            null,
            Map.of(
                    QUERY, query,
                    SAFE_SEARCH, safeSearch,
                    PAGE, page,
                    PER_PAGE, perPage,
                    IMAGE_TYPE, Photo.ImageType.photo,
                    ORDER, orderBy
            ),
            new ParameterizedTypeReference<SearchResult<Photo>>() {});
    return response.getBody();
  }

  public SearchResult<Photo> searchPhotos(@NonNull PhotoSearchQuery query) {
    return searchPhotos(query, DEFAULT_PAGE, DEFAULT_PER_PAGE);
  }
  public SearchResult<Photo> searchPhotos(@NonNull PhotoSearchQuery query, int page, int perPage) {
    if (StringUtils.isBlank(query.getTerm()) && query.getId() <= 0) {
      return new SearchResult<>();
    }

    Map<String, Object> queryParams = toQueryParamMap(query);
    queryParams.put(PAGE, page);
    queryParams.put(PER_PAGE, perPage);

    ResponseEntity<SearchResult<Photo>> response = performApiCall("",
            null,
            queryParams,
            new ParameterizedTypeReference<SearchResult<Photo>>() {});

    return response.getBody();
  }


  // --- VIDEOS --------------------------------------------------------------------------------------------------------

  private static final Video.VideoType DEFAULT_VIDEO_TYPE = Video.VideoType.all;
  private static final int DEFAULT_VIDEO_MIN_WIDTH = 0;
  private static final int DEFAULT_VIDEO_MIN_HEIGHT = 0;

  /**
   * Retrieve a single video.
   *
   * @param videoId The video’s ID. Required.
   *
   * @return the {@link Video} or <code>null</code>
   */
  public Video getVideoById(int videoId) {
    ResponseEntity<SearchResult<Video>> response = performApiCall("/videos",
            null,
            Map.of(ID, videoId),
            new ParameterizedTypeReference<SearchResult<Video>>() {});
    if (response.getStatusCode().equals(HttpStatus.OK)) {
      return Optional.ofNullable(response.getBody())
              .map(SearchResult::getHits)
              .map(l -> l.get(0))
              .orElse(null);
    } else {
      return null;
    }
  }

  public SearchResult<Video> searchVideos(@NonNull String query) {
    return searchVideos(query, DEFAULT_SAFE_SEARCH, DEFAULT_PAGE, DEFAULT_PER_PAGE, DEFAULT_ORDER);
  }

  public SearchResult<Video> searchVideos(@NonNull String query, boolean safeSearch, int page, int perPage, Entity.Order orderBy) {
    if (StringUtils.isBlank(query)) {
      return new SearchResult<>();
    }

    // Replace all whitespace in query with '+'
    query = query.trim().replaceAll("\\w", "+");

    ResponseEntity<SearchResult<Video>> response = performApiCall("/videos",
            null,
            Map.of(
                    QUERY, query,
                    SAFE_SEARCH, safeSearch,
                    PAGE, page,
                    PER_PAGE, perPage,
                    VIDEO_TYPE, DEFAULT_VIDEO_TYPE,
                    ORDER, orderBy
            ),
            new ParameterizedTypeReference<SearchResult<Video>>() {});
    return response.getBody();
  }

  public SearchResult<Video> searchVideos(@NonNull VideoSearchQuery query) {
    return searchVideos(query, DEFAULT_PAGE, DEFAULT_PER_PAGE);
  }

  public SearchResult<Video> searchVideos(@NonNull VideoSearchQuery query, int page, int perPage) {
    if (StringUtils.isBlank(query.getTerm()) && query.getId() <= 0) {
      return new SearchResult<>();
    }

    Map<String, Object> queryParams = toQueryParamMap(query);
    queryParams.put(PAGE, page);
    queryParams.put(PER_PAGE, perPage);

    ResponseEntity<SearchResult<Video>> response = performApiCall("/videos",
            null,
            queryParams,
            new ParameterizedTypeReference<SearchResult<Video>>() {});

    return response.getBody();
  }


  // --- INTERNAL ------------------------------------------------------------------------------------------------------

  private <T> ResponseEntity<T> performApiCall(String path, Class<T> responseType) {
    return performApiCall(path, Map.of(), Map.of(), responseType);
  }

  private <T> ResponseEntity<T> performApiCall(String path, Map<String, Object> pathVariables, Map<String, Object> queryParams, Class<T> responseType) {
    return performApiCall(path, pathVariables, queryParams, ParameterizedTypeReference.forType(responseType));
  }

  private <T> ResponseEntity<T> performApiCall(String path, Map<String, Object> pathVariables, Map<String, Object> queryParams, ParameterizedTypeReference<T> responseType) {
    if (pathVariables == null) {
      pathVariables = Map.of();
    }

    if (queryParams == null) {
      queryParams = Map.of();
    }

    // Build headers
    HttpHeaders headers = new HttpHeaders();

    // Build request entity
    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    // Build request URI
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(buildUrl(path));

    // Add query params
    uriBuilder.queryParam(KEY, apiKey);
    for (Map.Entry<String, Object> queryParam : queryParams.entrySet()) {
      String paramName = queryParam.getKey();
      Object paramValue = queryParam.getValue();
      if (paramName.equals(PER_PAGE)){

        // Check "per_page" param value between min and max
        Integer p = (Integer) paramValue;
        if (p < 0 || p > MAX_PER_PAGE) {
          paramValue = MAX_PER_PAGE;
        } else  if (p < MIN_PER_PAGE) {
          paramValue = MIN_PER_PAGE;
        }

      }

      uriBuilder.queryParam(paramName, paramValue);
    }

    // perform request
    String url = uriBuilder.buildAndExpand(pathVariables).toUriString();
    LOG.debug("GET - {}", url);
    //ResponseEntity<String> debugResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    return restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
  }

  private String buildUrl(String path) {
    return apiEndpoint + path;
  }

  private Map<String, Object> toQueryParamMap(@NonNull SearchQuery searchQuery) {
    Map<String, Object> result = new HashMap<>();
    if (searchQuery.getId() > 0) {
      result.put(ID, searchQuery.getId());
    } else {
      String searchTerm = searchQuery.getTerm().trim().replaceAll("\\s", "+");
      result.put(QUERY, searchTerm);
    }

    if (searchQuery instanceof PhotoSearchQuery) {
      PhotoSearchQuery photoQuery = (PhotoSearchQuery) searchQuery;
      result.put(IMAGE_TYPE, photoQuery.getImageType());
      result.put(ORIENTATION, photoQuery.getOrientation());
      result.put(CATEGORY, photoQuery.getCategory());
      result.put(COLORS, Joiner.on(",").join(photoQuery.getColors()));

    } else if (searchQuery instanceof VideoSearchQuery) {
      VideoSearchQuery videoQuery = (VideoSearchQuery) searchQuery;
      result.put(VIDEO_TYPE, videoQuery.getVideoType());
    }

    result.put(LANG, searchQuery.getLanguageCode());
    result.put(MIN_WIDTH, searchQuery.getMinWidth());
    result.put(MIN_HEIGHT, searchQuery.getMinHeight());
    result.put(EDITORS_CHOICE, searchQuery.isEditorsChoice());
    result.put(SAFE_SEARCH, searchQuery.isSafeSearch());

    return result;

  }

}
