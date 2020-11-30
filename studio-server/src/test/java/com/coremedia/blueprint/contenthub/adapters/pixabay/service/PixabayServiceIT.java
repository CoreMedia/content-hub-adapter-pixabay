package com.coremedia.blueprint.contenthub.adapters.pixabay.service;

import com.coremedia.blueprint.contenthub.adapters.pixabay.service.model.Entity;
import com.coremedia.blueprint.contenthub.adapters.pixabay.service.model.Photo;
import com.coremedia.blueprint.contenthub.adapters.pixabay.service.model.PhotoSearchQuery;
import com.coremedia.blueprint.contenthub.adapters.pixabay.service.model.SearchResult;
import com.coremedia.blueprint.contenthub.adapters.pixabay.service.model.Video;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PixabayServiceIT {

  private PixabayService testling;

  @Before
  public void setUp() {
    testling = new PixabayService("5615410-fc2a92ac13df16ce8f73f6ed1");
  }

  @Test
  public void testSearchPhotos() {
    SearchResult<Photo> hits = testling.searchPhotos("harley");
    assertNotNull(hits);
  }

  @Test
  public void testGetPhotoById() {
    int photoId = 4916080;
    Photo photo = testling.getPhotoById(photoId);
    assertNotNull(photo);
    assertEquals(photoId, photo.getId());
  }

  @Test
  public void testSearchVideos() {
    SearchResult<Video> hits = testling.searchVideos("fashion");
    assertNotNull(hits);
  }

  @Test
  public void testGetVideoById() {
    int videoId = 31377;
    Video video = testling.getVideoById(videoId);
    assertNotNull(video);
    assertEquals(videoId, video.getId());
  }

  @Test
  public void testSearchWithQuery() {
    PhotoSearchQuery query = PhotoSearchQuery.queryForTerm("beauty")
            .withCategory(Entity.Category.fashion)
            .withColor(Photo.Color.red)
            .withColor(Photo.Color.blue)
            .withEditorsChoice(false)
            .withSafeSearch(false)
            .withImageType(Photo.ImageType.photo)
            .withLanguageCode(Entity.LanguageCode.en)
            .withMinWidth(800)
            .withMinHeight(600)
            .withOrder(Entity.Order.latest)
            .withOrientation(Photo.Orientation.all);
    SearchResult<Photo> results = testling.searchPhotos(query);
    assertNotNull(results);
  }


}
