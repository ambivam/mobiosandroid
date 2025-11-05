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
        return resolveVariables(value);
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
    
    /**
     * Resolves variables in property values using ${variable.name} syntax
     */
    private String resolveVariables(String value) {
        if (value == null || !value.contains("${")) {
            return value;
        }
        
        String resolved = value;
        int maxIterations = 10; // Prevent infinite loops
        int iterations = 0;
        
        while (resolved.contains("${") && iterations < maxIterations) {
            int start = resolved.indexOf("${");
            int end = resolved.indexOf("}", start);
            
            if (end == -1) {
                break; // Malformed variable reference
            }
            
            String variableName = resolved.substring(start + 2, end);
            String variableValue = properties.getProperty(variableName);
            
            if (variableValue != null) {
                resolved = resolved.substring(0, start) + variableValue + resolved.substring(end + 1);
            } else {
                // Variable not found, leave as is
                break;
            }
            
            iterations++;
        }
        
        return resolved;
    }
}
