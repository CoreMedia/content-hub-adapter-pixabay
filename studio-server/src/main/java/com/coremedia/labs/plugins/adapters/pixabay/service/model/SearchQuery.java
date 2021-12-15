package com.coremedia.labs.plugins.adapters.pixabay.service.model;

public abstract class SearchQuery {

  protected String term;
  protected int id = -1;
  protected Entity.LanguageCode languageCode = Entity.LanguageCode.en;
  protected Entity.Category category;
  protected int minWidth = 0;
  protected int minHeight = 0;
  protected boolean isEditorsChoice = false;
  protected boolean isSafeSearch = false;
  protected Entity.Order order = Entity.Order.popular;

  public String getTerm() {
    return term;
  }

  public int getId() {
    return id;
  }

  public Entity.LanguageCode getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(Entity.LanguageCode languageCode) {
    this.languageCode = languageCode;
  }

  public Entity.Category getCategory() {
    return category;
  }

  public void setCategory(Entity.Category category) {
    this.category = category;
  }

  public int getMinWidth() {
    return minWidth;
  }

  public void setMinWidth(int minWidth) {
    this.minWidth = minWidth;
  }

  public int getMinHeight() {
    return minHeight;
  }

  public void setMinHeight(int minHeight) {
    this.minHeight = minHeight;
  }

  public boolean isEditorsChoice() {
    return isEditorsChoice;
  }

  public void setEditorsChoice(boolean editorsChoice) {
    isEditorsChoice = editorsChoice;
  }

  public boolean isSafeSearch() {
    return isSafeSearch;
  }

  public void setSafeSearch(boolean safeSearch) {
    isSafeSearch = safeSearch;
  }

  public Entity.Order getOrder() {
    return order;
  }

  public void setOrder(Entity.Order order) {
    this.order = order;
  }
}
