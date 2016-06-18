package com.blazarquant.bfp.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Wojciech Zankowski
 */
public class PathResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathResolver.class);

    private final String appDirectory;

    public PathResolver(String systemProperty) {
        String appDirectory = System.getProperty(systemProperty);
        if (appDirectory == null) {
            appDirectory = "src" + File.separator + "test";
        }
        this.appDirectory = appDirectory;

        File resultFile = new File(appDirectory);
        if (!resultFile.exists()) {
            LOGGER.warn("'{}' does not exist!", appDirectory);
        }
        if (!resultFile.isDirectory()) {
            LOGGER.warn("'{}' is not a directory!", appDirectory);
        }
    }

    public String getAppDirectory() {
        return appDirectory;
    }
}
