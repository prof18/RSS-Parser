// see https://kotlinlang.org/docs/js-project-setup.html#webpack-configuration-file

const path = require("path");

const basePath = config.basePath;
const projectPath = path.resolve(basePath, "..", "..", "..", "..");
const testResourcesPath = path.resolve(projectPath, "rssparser", "src", "commonTest", "resources");

// Set up proxy to serve test resources
config.proxies = config.proxies || {};
config.proxies["/test-resources"] = testResourcesPath;

// Only serve XML files from test resources
config.files = [
    // Serve test resources directly 
    {pattern: path.resolve(testResourcesPath, "**/*.xml"), included: false, served: true, watched: false},
].concat(config.files);

function KarmaWebpackOutputFramework(config) {
    // This controller is instantiated and set during the preprocessor phase.
    const controller = config.__karmaWebpackController;

    // only if webpack has instantiated its controller
    if (!controller) {
        console.warn(
            "Webpack has not instantiated controller yet.\n" +
            "Check if you have enabled webpack preprocessor and framework before this framework"
        )
        return
    }

    config.files.push({
        pattern: `${controller.outputPath}/**/*`,
        included: false,
        served: true,
        watched: false
    })
}

const KarmaWebpackOutputPlugin = {
    'framework:webpack-output': ['factory', KarmaWebpackOutputFramework],
};

config.plugins.push(KarmaWebpackOutputPlugin);
config.frameworks.push("webpack-output");