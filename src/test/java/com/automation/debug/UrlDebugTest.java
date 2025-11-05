package com.automation.debug;

import com.automation.config.ConfigManager;
import org.testng.annotations.Test;

public class UrlDebugTest {
    
    @Test
    public void debugPerfectoUrl() {
        try {
            ConfigManager config = new ConfigManager();
            config.loadConfig("perfecto");
            
            System.out.println("=== PERFECTO CONFIGURATION DEBUG ===");
            System.out.println("perfecto.cloud.name: " + config.getProperty("perfecto.cloud.name"));
            System.out.println("perfecto.url (raw): " + config.getProperty("perfecto.url"));
            System.out.println("perfecto.security.token (first 30 chars): " + 
                config.getProperty("perfecto.security.token").substring(0, 30) + "...");
            
            // Test variable resolution
            String url = config.getProperty("perfecto.url");
            System.out.println("Resolved URL: " + url);
            
            // Show device configuration
            System.out.println("Android Device ID: " + config.getProperty("perfecto.android.device.id"));
            System.out.println("Android OS Version: " + config.getProperty("perfecto.android.os.version"));
            System.out.println("Android App Path: " + config.getProperty("perfecto.android.app.path"));
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
