package com.blazarquant.bfp.database.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Wojciech Zankowski
 */
public class DatabasePropertiesLoader {

    private static final String CONFIG_PATH = System.getProperty("jboss.server.base.dir") + "/config/settings.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabasePropertiesLoader.class);

    private static Properties properties;

    public static Properties getProperties() {
        if (properties == null) {
            try (FileInputStream inputStream = new FileInputStream(new File(CONFIG_PATH))) {
                properties = new Properties();
                properties.load(inputStream);
            } catch (IOException e) {
                LOGGER.error("Failed to load database properties. {}", e);
            }
        }
        return properties;
    }

    public static String getProperty(String propertyName) {
        String property = properties.getProperty(propertyName);
        if (property == null) {
            throw new IllegalArgumentException("Illegal property name. Property " + propertyName + " does not seem to exists.");
        }
        return property;
    }

}
