package net.openblazar.bfp.database.utils;

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

    private static final String CONFIG_PATH = System.getProperty("jboss.server.base.dir")+"/config/settings.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabasePropertiesLoader.class);


    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(new File(CONFIG_PATH))) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Failed to load database properties. {}", e);
        }
        return properties;
    }

}
