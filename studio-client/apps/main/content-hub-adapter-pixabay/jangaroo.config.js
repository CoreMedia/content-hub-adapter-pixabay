const { jangarooConfig } = require("@jangaroo/core");

module.exports = jangarooConfig({
  type: "code",
  sencha: {
    name: "com.coremedia.labs.plugins__studio-client.content-hub-adapter-pixabay",
    namespace: "com.coremedia.labs.plugins.adapters.pixabay.client",
    studioPlugins: [
      {
        mainClass: "com.coremedia.labs.plugins.adapters.pixabay.client.ContentHubPixabayStudioPlugin",
        name: "Content Hub - Pixabay",
      },
    ],
  },
  command: {
    build: {
      ignoreTypeErrors: true
    },
  },
});
