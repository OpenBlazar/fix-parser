package com.blazarquant.bfp.core.security.util;

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

    private static final String CONFIG_PATH = System.getProperty("jboss.server.base.dir") + "/config/blazarfixparser.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsManager.class);

    private static Properties properties;

    public SettingsManager() {
        this.properties = loadProperties();
    }

    private Properties loadProperties() {
        Properties properties = null;
        try (FileInputStream inputStream = new FileInputStream(new File(CONFIG_PATH))) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Failed to load database properties. {}", e);
        }
        return properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(Settings setting) {
        String property = properties.getProperty(setting.getProperty());
        if (property == null) {
            throw new IllegalArgumentException("Illegal property name. Property " + setting.name() + " does not seem to exists.");
        }
        return property;
    }

}
