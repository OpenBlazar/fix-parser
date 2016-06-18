package com.blazarquant.bfp.core.security.util;

import com.blazarquant.bfp.common.BlazarFixParserConstants;
import com.blazarquant.bfp.common.Directories;
import com.blazarquant.bfp.common.PathResolver;
import com.blazarquant.bfp.common.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Wojciech Zankowski
 */
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
