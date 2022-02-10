import SvgIconUtil from "@coremedia/studio-client.base-models/util/SvgIconUtil";
import CoreIcons_properties from "@coremedia/studio-client.core-icons/CoreIcons_properties";
import icon from "./icons/pixabay_16.svg";
import photo from "./icons/content-hub-photo_16.svg";
import video from "./icons/content-hub-video_16.svg";

interface ContentHubPixabay_properties {

  adapter_type_pixabay_name: string;
  adapter_type_pixabay_icon: string;
  folder_type_photo_name: string;
  folder_type_photo_icon: string;
  folder_type_video_name: string;
  folder_type_video_icon: string;
  item_type_photo_name: string;
  item_type_photo_icon: string;
  item_type_video_name: string;
  item_type_video_icon: string;
  stats_sectionName: string;
  dimensions_sectionItemKey: string;
  user_sectionItemKey: string;
  views_sectionItemKey: string;
  downloads_sectionItemKey: string;
  favorites_sectionItemKey: string;
  likes_sectionItemKey: string;
  comments_sectionItemKey: string;
  duration_sectionItemKey: string;
  page_sectionItemKey: string;
}

/**
 * Singleton for the current user Locale's instance of ResourceBundle "ContentHubPixabay".
 * @see ContentHubPixabay_properties
 */
const ContentHubPixabay_properties: ContentHubPixabay_properties = {
  adapter_type_pixabay_name: "Pixabay",
  adapter_type_pixabay_icon: SvgIconUtil.getIconStyleClassForSvgIcon(icon),
  folder_type_photo_name: "Photos",
  folder_type_photo_icon: CoreIcons_properties.personal_folder_picture,
  folder_type_video_name: "Videos",
  folder_type_video_icon: CoreIcons_properties.personal_folder_video,
  item_type_photo_name: "Photo",
  item_type_photo_icon: SvgIconUtil.getIconStyleClassForSvgIcon(photo),
  item_type_video_name: "Video",
  item_type_video_icon: SvgIconUtil.getIconStyleClassForSvgIcon(video),
  stats_sectionName: "Stats",
  dimensions_sectionItemKey: "Dimensions",
  user_sectionItemKey: "User",
  views_sectionItemKey: "Views",
  downloads_sectionItemKey: "Downloads",
  favorites_sectionItemKey: "Favorites",
  likes_sectionItemKey: "Likes",
  comments_sectionItemKey: "Comments",
  duration_sectionItemKey: "Duration",
  page_sectionItemKey: "Page",
};

export default ContentHubPixabay_properties;
