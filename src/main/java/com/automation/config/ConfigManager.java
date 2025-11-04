package com.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private Properties properties = new Properties();

    public void loadConfig(String platform) {
        String configFile = getConfigFile(platform);
        
        try (FileInputStream fis = new FileInputStream(configFile)) {
            properties.load(fis);
            System.out.println("Loaded config: " + configFile);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config file: " + configFile, e);
        }
    }

    private String getConfigFile(String platform) {
        switch (platform.toLowerCase()) {
            case "android":
                return "src/test/resources/config/android.properties";
            case "android-emulator":
                return "src/test/resources/config/android-emulator.properties";
            case "ios":
                return "src/test/resources/config/ios.properties";
            case "browserstack":
                return "src/test/resources/config/browserstack.properties";
            case "perfecto":
                return "src/test/resources/config/perfecto.properties";
            default:
                throw new RuntimeException("Unknown platform: " + platform);
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property not found: " + key);
        }
        return value;
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }
}
