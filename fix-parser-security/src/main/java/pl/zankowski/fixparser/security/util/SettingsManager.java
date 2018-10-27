package pl.zankowski.fixparser.security.util;

import pl.zankowski.bfp.common.BlazarFixParserConstants;
import pl.zankowski.bfp.common.Directories;
import pl.zankowski.bfp.common.PathResolver;
import pl.zankowski.bfp.common.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsManager.class);

    private final PathResolver pathResolver = new PathResolver(BlazarFixParserConstants.WIDLFLY_BASE_DIR);
    private Properties properties;

    private static class SettingsManagerHolder {
        static final SettingsManager INSTANCE = new SettingsManager();
    }

    public static SettingsManager getInstance() {
        return SettingsManagerHolder.INSTANCE;
    }

    private SettingsManager() {
        this.properties = loadProperties(
                PathUtils.joinPath(pathResolver.getAppDirectory(), Directories.CONFIG.getDirName(), "blazarfixparser.properties")
        );
    }

    private Properties loadProperties(String configPath) {
        Properties properties = null;
        try (FileInputStream inputStream = new FileInputStream(new File(configPath))) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Failed to load properties. {}", e);
        }
        return properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public PathResolver getPathResolver() {
        return pathResolver;
    }
}
