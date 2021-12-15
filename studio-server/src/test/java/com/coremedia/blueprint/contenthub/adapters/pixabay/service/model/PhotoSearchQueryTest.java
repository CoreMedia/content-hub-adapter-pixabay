package com.coremedia.blueprint.contenthub.adapters.pixabay.service.model;

import com.coremedia.labs.plugins.adapters.pixabay.service.model.Entity;
import com.coremedia.labs.plugins.adapters.pixabay.service.model.Photo;
import com.coremedia.labs.plugins.adapters.pixabay.service.model.PhotoSearchQuery;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhotoSearchQueryTest {

  @Test
  public void testQueryForTerm() {
    String searchTerm = "kitten";
    PhotoSearchQuery kittenQuery = PhotoSearchQuery.queryForTerm(searchTerm);
    assertEquals(searchTerm, kittenQuery.getTerm());
    assertEquals(-1, kittenQuery.getId());

    assertEquals(Entity.LanguageCode.en, kittenQuery.getLanguageCode());
    assertEquals(Photo.ImageType.photo, kittenQuery.getImageType());
    assertEquals(Photo.Orientation.all, kittenQuery.getOrientation());
    assertNull(kittenQuery.getCategory());
    assertEquals(0, kittenQuery.getMinWidth());
    assertEquals(0, kittenQuery.getMinHeight());
    assertEquals(Collections.emptyList(), kittenQuery.getColors());
    assertFalse(kittenQuery.isEditorsChoice());
    assertFalse(kittenQuery.isSafeSearch());
    assertEquals(Entity.Order.popular, kittenQuery.getOrder());
  }

  @Test
  public void testQueryForId() {
    int searchId = 4916080;
    PhotoSearchQuery kittenQuery = PhotoSearchQuery.queryForId(searchId);
    assertNull(kittenQuery.getTerm());
    assertEquals(searchId, kittenQuery.getId());

    assertEquals(Entity.LanguageCode.en, kittenQuery.getLanguageCode());
    assertEquals(Photo.ImageType.photo, kittenQuery.getImageType());
    assertEquals(Photo.Orientation.all, kittenQuery.getOrientation());
    assertNull(kittenQuery.getCategory());
    assertEquals(0, kittenQuery.getMinWidth());
    assertEquals(0, kittenQuery.getMinHeight());
    assertEquals(Collections.emptyList(), kittenQuery.getColors());
    assertFalse(kittenQuery.isEditorsChoice());
    assertFalse(kittenQuery.isSafeSearch());
    assertEquals(Entity.Order.popular, kittenQuery.getOrder());
  }

  @Test
  public void testAllFlags() {
    String searchTerm = "mode";
    PhotoSearchQuery searchQuery = PhotoSearchQuery.queryForTerm(searchTerm)
            .withLanguageCode(Entity.LanguageCode.de)
            .withImageType(Photo.ImageType.photo)
            .withOrientation(Photo.Orientation.horizontal)
            .withCategory(Entity.Category.backgrounds)
            .withMinWidth(800)
            .withMinHeight(600)
            .withColor(Photo.Color.black)
            .withColor(Photo.Color.red)
            .withEditorsChoice(false)
            .withSafeSearch(true)
            .withOrder(Entity.Order.latest);
    assertEquals(searchTerm, searchQuery.getTerm());
    assertEquals(-1, searchQuery.getId());
    assertEquals(Entity.LanguageCode.de, searchQuery.getLanguageCode());
    assertEquals(Photo.ImageType.photo, searchQuery.getImageType());
    assertEquals(Photo.Orientation.horizontal, searchQuery.getOrientation());
    assertEquals(Entity.Category.backgrounds, searchQuery.getCategory());
    assertEquals(800, searchQuery.getMinWidth());
    assertEquals(600, searchQuery.getMinHeight());
    assertEquals(2, searchQuery.getColors().size());
    assertEquals(Photo.Color.black, searchQuery.getColors().get(0));
    assertEquals(Photo.Color.red, searchQuery.getColors().get(1));
    assertFalse(searchQuery.isEditorsChoice());
    assertTrue(searchQuery.isSafeSearch());
    assertEquals(Entity.Order.latest, searchQuery.getOrder());
  }

}
