import ContentHub_properties from "@coremedia/studio-client.main.content-hub-editor-components/ContentHub_properties";
import CopyResourceBundleProperties from "@coremedia/studio-client.main.editor-components/configuration/CopyResourceBundleProperties";
import StudioPlugin from "@coremedia/studio-client.main.editor-components/configuration/StudioPlugin";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
import ContentHubPixabay_properties from "./ContentHubPixabay_properties";

interface ContentHubPixabayStudioPluginConfig extends Config<StudioPlugin> {
}

class ContentHubPixabayStudioPlugin extends StudioPlugin {
  declare Config: ContentHubPixabayStudioPluginConfig;

  static readonly xtype: string = "com.coremedia.labs.plugins.contenthub.pixabay.ContentHubPixabayStudioPlugin";

  constructor(config: Config<ContentHubPixabayStudioPlugin> = null) {
    super(ConfigUtils.apply(Config(ContentHubPixabayStudioPlugin, {

      configuration: [
        new CopyResourceBundleProperties({
          destination: resourceManager.getResourceBundle(null, ContentHub_properties),
          source: resourceManager.getResourceBundle(null, ContentHubPixabay_properties),
        }),
      ],

    }), config));
  }
}

export default ContentHubPixabayStudioPlugin;
