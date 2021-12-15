package com.coremedia.labs.plugins.adapters.pixabay.model;

import com.coremedia.contenthub.api.ContentHubObjectId;
import com.coremedia.contenthub.api.ContentHubType;
import com.coremedia.contenthub.api.Folder;
import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PixabayFolder extends PixabayContentHubObject implements Folder {

  private final ContentHubType type;
  private final String name;
  private List<Folder> subfolders;

  public PixabayFolder(@NonNull ContentHubObjectId objectId, String name, PixabayContentHubType type) {
    super(objectId);
    this.name = name;
    this.type = type.getType();
    this.subfolders = new ArrayList<>();
  }

  @Override
  public String getName() {
    return name;
  }

  @NonNull
  @Override
  public ContentHubType getContentHubType() {
    return type;
  }

  public List<Folder> getSubfolders() {
    return subfolders;
  }

  public void setSubFolders(List<Folder> subfolders) {
    this.subfolders = subfolders;
  }

  public void addSubfolder(Folder folder) {
    this.subfolders.add(folder);
  }

}
