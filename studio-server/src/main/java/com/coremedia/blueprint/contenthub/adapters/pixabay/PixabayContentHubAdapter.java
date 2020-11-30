package com.coremedia.blueprint.contenthub.adapters.pixabay;

import com.coremedia.blueprint.contenthub.adapters.pixabay.model.PixabayContentHubType;
import com.coremedia.blueprint.contenthub.adapters.pixabay.model.PixabayFolder;
import com.coremedia.blueprint.contenthub.adapters.pixabay.model.PixabayPhotoItem;
import com.coremedia.blueprint.contenthub.adapters.pixabay.model.PixabaySearchFolder;
import com.coremedia.blueprint.contenthub.adapters.pixabay.model.PixabayVideoItem;
import com.coremedia.blueprint.contenthub.adapters.pixabay.service.PixabayService;
import com.coremedia.blueprint.contenthub.adapters.pixabay.service.model.Entity;
import com.coremedia.blueprint.contenthub.adapters.pixabay.service.model.Photo;
import com.coremedia.blueprint.contenthub.adapters.pixabay.service.model.PhotoSearchQuery;
import com.coremedia.blueprint.contenthub.adapters.pixabay.service.model.SearchQuery;
import com.coremedia.blueprint.contenthub.adapters.pixabay.service.model.SearchResult;
import com.coremedia.blueprint.contenthub.adapters.pixabay.service.model.Video;
import com.coremedia.blueprint.contenthub.adapters.pixabay.service.model.VideoSearchQuery;
import com.coremedia.contenthub.api.ContentHubAdapter;
import com.coremedia.contenthub.api.ContentHubContext;
import com.coremedia.contenthub.api.ContentHubObject;
import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.ContentHubTransformer;
import com.coremedia.contenthub.api.ContentHubType;
import com.coremedia.contenthub.api.Folder;
import com.coremedia.contenthub.api.GetChildrenResult;
import com.coremedia.contenthub.api.Item;
import com.coremedia.contenthub.api.exception.ContentHubException;
import com.coremedia.contenthub.api.pagination.PaginationRequest;
import com.coremedia.contenthub.api.search.ContentHubSearchResult;
import com.coremedia.contenthub.api.search.ContentHubSearchService;
import com.coremedia.contenthub.api.search.Sort;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class PixabayContentHubAdapter implements ContentHubAdapter, ContentHubSearchService {

  private static final Logger LOG = LoggerFactory.getLogger(PixabayContentHubAdapter.class);

  private final String connectionId;
  private final PixabayContentHubSettings settings;

  private PixabayService pixabayService;

  private PixabayFolder rootFolder;
  private PixabayFolder photosRootFolder;
  private PixabayFolder videosRootFolder;

  public PixabayContentHubAdapter(PixabayContentHubSettings settings, String connectionId) {
    this.settings = settings;
    this.connectionId = connectionId;

    rootFolder = new PixabayFolder(new ContentHubObjectId(connectionId, "root"), settings.getDisplayName(), PixabayContentHubType.FOLDER);
    photosRootFolder = new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos"), "Photos", PhotoSearchQuery.queryForTerm("*"), PixabayContentHubType.PHOTO);
    videosRootFolder = new PixabaySearchFolder(new ContentHubObjectId(connectionId, "video"), "Videos", VideoSearchQuery.queryForTerm("*"), PixabayContentHubType.VIDEO);

    rootFolder.addSubfolder(photosRootFolder);
    rootFolder.addSubfolder(videosRootFolder);

    boolean safeSearch = settings.isSafeSearch();

    // Photo categories
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-backgrounds"), "Backgrounds", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.backgrounds).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-fashion"), "Fashion", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.fashion).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-nature"), "Nature", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.nature).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-science"), "Science", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.science).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-education"), "Education", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.education).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-feelings"), "Feelings", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.feelings).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-health"), "Health", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.health).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-people"), "People", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.people).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-religion"), "Religion", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.religion).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-animals"), "Animals", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.animals).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-industry"), "Industry", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.industry).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-computer"), "Computer", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.computer).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-food"), "Food", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.food).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-sports"), "Sports", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.sports).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-transportation"), "Transportation", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.transportation).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-travel"), "Travel", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.travel).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-buildings"), "Buildings", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.buildings).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-business"), "Business", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.business).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));
    photosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "photos-music"), "Music", PhotoSearchQuery.queryForTerm("*").withCategory(Entity.Category.music).withSafeSearch(safeSearch), PixabayContentHubType.PHOTO));

    // Video categories
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-backgrounds"), "Backgrounds", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.backgrounds).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-fashion"), "Fashion", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.fashion).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-nature"), "Nature", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.nature).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-science"), "Science", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.science).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-education"), "Education", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.education).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-feelings"), "Feelings", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.feelings).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-health"), "Health", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.health).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-people"), "People", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.people).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-religion"), "Religion", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.religion).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-animals"), "Animals", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.animals).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-industry"), "Industry", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.industry).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-computer"), "Computer", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.computer).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-food"), "Food", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.food).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-sports"), "Sports", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.sports).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-transportation"), "Transportation", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.transportation).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-travel"), "Travel", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.travel).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-buildings"), "Buildings", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.buildings).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-business"), "Business", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.business).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));
    videosRootFolder.addSubfolder(new PixabaySearchFolder(new ContentHubObjectId(connectionId, "videos-music"), "Music", VideoSearchQuery.queryForTerm("*").withCategory(Entity.Category.music).withSafeSearch(safeSearch), PixabayContentHubType.VIDEO));

    pixabayService = new PixabayService(settings.getApiKey());
  }

  // --- ContentHubAdapter ---------------------------------------------------------------------------------------------

  @Override
  public Folder getRootFolder(ContentHubContext context) throws ContentHubException {
    return rootFolder;
  }

  @Nullable
  @Override
  public Folder getFolder(ContentHubContext context, ContentHubObjectId id) throws ContentHubException {
    return getRootFolder(context);
  }

  public List<Folder> getSubFolders(ContentHubContext context, Folder folder) throws ContentHubException {
    if (folder instanceof PixabayFolder) {
      PixabayFolder parent = (PixabayFolder) folder;
      return parent.getSubfolders();
    }
    return Collections.emptyList();
  }

  @Nullable
  @Override
  public Folder getParent(ContentHubContext context, ContentHubObject contentHubObject) throws ContentHubException {
    return rootFolder == contentHubObject ? null : getRootFolder(context);
  }

  public List<Item> getItems(ContentHubContext context, Folder folder) throws ContentHubException {
    List<Item> items = Collections.emptyList();

    try {
      if (rootFolder == folder) {
        items = pixabayService.searchPhotos(PhotoSearchQuery.queryForTerm("*").withSafeSearch(settings.isSafeSearch())).getHits()
                .stream()
                .map(this::createPhotoItem)
                .collect(Collectors.toUnmodifiableList());
      } else if (folder instanceof PixabaySearchFolder) {
        PixabaySearchFolder searchFolder = (PixabaySearchFolder) folder;
        SearchQuery query = searchFolder.getQuery();

        if (query instanceof PhotoSearchQuery) {
          items = pixabayService.searchPhotos((PhotoSearchQuery) query).getHits()
                  .stream().map(this::createPhotoItem)
                  .collect(Collectors.toUnmodifiableList());
        } else if (query instanceof VideoSearchQuery) {
          pixabayService.searchVideos((VideoSearchQuery) query);
          items = pixabayService.searchVideos((VideoSearchQuery) query).getHits()
                  .stream().map(this::createVideoItem)
                  .collect(Collectors.toUnmodifiableList());
        }

      }

    } catch (Exception e) {
      LOG.warn("Unable to get items for folder {}. {}", folder, e);
    }

    return items;
  }

  @Nullable
  @Override
  public Item getItem(ContentHubContext context, ContentHubObjectId id) throws ContentHubException {
    Item result = null;
    String[] externalId = id.getExternalId().split("-");
    if (externalId.length == 2) {
      if ("photo".equals(externalId[0])) {
        result = Optional.ofNullable(pixabayService.getPhotoById(Integer.parseInt(externalId[1]))).map(this::createPhotoItem).orElse(null);
      } else if ("video".equals(externalId[0])) {
        result = Optional.ofNullable(pixabayService.getVideoById(Integer.parseInt(externalId[1]))).map(this::createVideoItem).orElse(null);
      }
    }
    return result;
  }

  @Override
  public GetChildrenResult getChildren(ContentHubContext context, Folder folder, @Nullable PaginationRequest paginationRequest) {
    List<ContentHubObject> children = new ArrayList<>();
    children.addAll(getSubFolders(context, folder));
    children.addAll(getItems(context, folder));
    return new GetChildrenResult(children);
  }

  @Override
  public ContentHubTransformer transformer() {
    return new PixabayContentHubTransformer();
  }


  // --- ContentHubSearchService ---------------------------------------------------------------------------------------

  private static final List<ContentHubType> SEARCH_TYPES = List.of(
          PixabayContentHubType.PHOTO.getType(),
          PixabayContentHubType.VIDEO.getType()
  );

  @NonNull
  @Override
  public Optional<ContentHubSearchService> searchService() {
    return Optional.of(this);
  }

  @Override
  public ContentHubSearchResult search(@NonNull String query,
                                       @Nullable Folder belowFolder,
                                       @Nullable ContentHubType type,
                                       Collection<String> filterQueries,
                                       List<Sort> sortCriteria,
                                       int limit) {

    ContentHubSearchResult result = new ContentHubSearchResult(Collections.emptyList());

    if (StringUtils.isBlank(query)) {
      return result;
    }

    // Search for id?
    int entityId = 0;
    if (query.startsWith("id:")) {
      String itemId = query.replace("id:", "");
      try {
        entityId = Integer.parseInt(itemId);
      } catch (NumberFormatException e) {
        // ignore
      }
    }

    // Photo Search
    if (PixabayContentHubType.PHOTO.getType().equals(type)) {
      SearchResult<Photo> searchResult;

      if (entityId > 0) {
        searchResult = pixabayService.searchPhotos(PhotoSearchQuery.queryForId(entityId));

      } else if (belowFolder instanceof PixabaySearchFolder) {
        PixabaySearchFolder belowSearchFolder = (PixabaySearchFolder) belowFolder;
        PhotoSearchQuery sq = PhotoSearchQuery.fromQuery((PhotoSearchQuery) belowSearchFolder.getQuery());
        sq.withTerm(query);
        searchResult = pixabayService.searchPhotos(sq);

      } else {
        searchResult = pixabayService.searchPhotos(query, settings.isSafeSearch(), 1, limit, Entity.Order.latest);
      }

      if (searchResult != null && searchResult.getHits() != null) {
        result = new ContentHubSearchResult(
                searchResult.getHits()
                        .stream()
                        .map(this::createPhotoItem)
                        .collect(Collectors.toUnmodifiableList()));
      }

      // Video Search
    } else if (PixabayContentHubType.VIDEO.getType().equals(type)) {
      SearchResult<Video> searchResult;

      if (entityId > 0) {
        searchResult = pixabayService.searchVideos(VideoSearchQuery.queryForId(entityId));

      } else if (belowFolder instanceof PixabaySearchFolder) {
        PixabaySearchFolder belowSearchFolder = (PixabaySearchFolder) belowFolder;
        VideoSearchQuery sq = VideoSearchQuery.fromQuery((VideoSearchQuery) belowSearchFolder.getQuery());
        sq.withTerm(query);
        searchResult = pixabayService.searchVideos(sq);

      } else {
        searchResult = pixabayService.searchVideos(query, settings.isSafeSearch(), 1, limit, Entity.Order.latest);
      }

      if (searchResult != null && searchResult.getHits() != null) {
        result = new ContentHubSearchResult(
                searchResult.getHits()
                        .stream()
                        .map(this::createVideoItem)
                        .collect(Collectors.toUnmodifiableList()));
      }
    }

    return result;
  }

  @Override
  public boolean supportsSearchBelowFolder() {
    return true;
  }

  @Override
  public Collection<ContentHubType> supportedTypes() {
    return SEARCH_TYPES;
  }


  // --- INTERNAL ------------------------------------------------------------------------------------------------------

  private PixabayPhotoItem createPhotoItem(@NonNull Photo photo) {
    ContentHubObjectId hubId = new ContentHubObjectId(connectionId, "photo-" + photo.getId());
    return new PixabayPhotoItem(hubId, photo);
  }

  private PixabayVideoItem createVideoItem(@NonNull Video video) {
    ContentHubObjectId hubId = new ContentHubObjectId(connectionId, "video-" + video.getId());
    return new PixabayVideoItem(hubId, video);
  }

}
