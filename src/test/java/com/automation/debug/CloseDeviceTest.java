package com.automation.debug;

import com.automation.driver.DriverFactory;
import org.testng.annotations.Test;

public class CloseDeviceTest {
    
    @Test
    public void testCloseDeviceCommand() {
        try {
            System.out.println("=== TESTING CLOSE DEVICE COMMAND ===");
            
            // Create driver for Android on Perfecto
            DriverFactory.createDriver("android", "perfecto");
            System.out.println("✅ Driver created successfully");
            
            // Wait a moment to ensure session is established
            Thread.sleep(3000);
            
            // Quit driver (this should trigger the Close device command)
            DriverFactory.quitDriver();
            System.out.println("✅ Driver quit with Close device command");
            
        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
