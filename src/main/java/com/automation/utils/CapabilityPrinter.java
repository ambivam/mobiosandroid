package com.automation.utils;

import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.Map;

/**
 * Utility to print and verify desired capabilities
 * Helpful for debugging and understanding what capabilities are being sent to Appium
 */
public class CapabilityPrinter {

    /**
     * Print all capabilities in a readable format
     */
    public static void printCapabilities(DesiredCapabilities caps, String executionType) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DESIRED CAPABILITIES - " + executionType.toUpperCase());
        System.out.println("=".repeat(60));
        
        Map<String, Object> capMap = caps.asMap();
        
        // Core capabilities
        System.out.println("\n📱 CORE CAPABILITIES:");
        printCapability(capMap, "platformName");
        printCapability(capMap, "appium:automationName");
        printCapability(capMap, "appium:udid");
        printCapability(capMap, "appium:deviceName");
        printCapability(capMap, "appium:platformVersion");
        
        // App capabilities
        System.out.println("\n📦 APP CAPABILITIES:");
        printCapability(capMap, "appium:appPackage");
        printCapability(capMap, "appium:appActivity");
        printCapability(capMap, "appium:app");
        
        // Emulator specific
        if ("emulator".equalsIgnoreCase(executionType)) {
            System.out.println("\n🖥️ EMULATOR CAPABILITIES:");
            printCapability(capMap, "appium:avd");
            printCapability(capMap, "appium:noReset");
            printCapability(capMap, "appium:fullReset");
            printCapability(capMap, "appium:autoGrantPermissions");
            printCapability(capMap, "appium:autoAcceptAlerts");
            printCapability(capMap, "appium:newCommandTimeout");
            printCapability(capMap, "appium:appWaitTimeout");
            printCapability(capMap, "appium:appWaitActivity");
        }
        
        // BrowserStack specific
        if ("browserstack".equalsIgnoreCase(executionType)) {
            System.out.println("\n☁️ BROWSERSTACK CAPABILITIES:");
            printCapability(capMap, "browserstack.user");
            printCapability(capMap, "browserstack.key");
            printCapability(capMap, "project");
            printCapability(capMap, "build");
            printCapability(capMap, "device");
            printCapability(capMap, "os_version");
            printCapability(capMap, "app");
        }
        
        // Additional capabilities
        System.out.println("\n⚙️ OTHER CAPABILITIES:");
        for (Map.Entry<String, Object> entry : capMap.entrySet()) {
            String key = entry.getKey();
            if (!isCoreCap(key) && !isAppCap(key) && !isEmulatorCap(key) && !isBrowserStackCap(key)) {
                printCapability(capMap, key);
            }
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Total Capabilities: " + capMap.size());
        System.out.println("=".repeat(60) + "\n");
    }
    
    private static void printCapability(Map<String, Object> capMap, String key) {
        Object value = capMap.get(key);
        if (value != null) {
            System.out.println("  " + key + " = " + value + " (" + value.getClass().getSimpleName() + ")");
        }
    }
    
    private static boolean isCoreCap(String key) {
        return key.equals("platformName") || key.equals("appium:automationName") || 
               key.equals("appium:udid") || key.equals("appium:deviceName") || 
               key.equals("appium:platformVersion");
    }
    
    private static boolean isAppCap(String key) {
        return key.equals("appium:appPackage") || key.equals("appium:appActivity") || 
               key.equals("appium:app");
    }
    
    private static boolean isEmulatorCap(String key) {
        return key.startsWith("appium:avd") || key.startsWith("appium:noReset") || 
               key.startsWith("appium:fullReset") || key.startsWith("appium:autoGrant") || 
               key.startsWith("appium:autoAccept") || key.startsWith("appium:newCommand") ||
               key.startsWith("appium:appWait");
    }
    
    private static boolean isBrowserStackCap(String key) {
        return key.startsWith("browserstack") || key.equals("project") || 
               key.equals("build") || key.equals("device") || key.equals("os_version");
    }
    
    /**
     * Validate required capabilities for emulator
     */
    public static boolean validateEmulatorCapabilities(DesiredCapabilities caps) {
        System.out.println("\n🔍 VALIDATING EMULATOR CAPABILITIES...");
        
        boolean valid = true;
        String[] requiredCaps = {
            "platformName",
            "appium:automationName", 
            "appium:udid",
            "appium:deviceName"
        };
        
        for (String cap : requiredCaps) {
            if (caps.getCapability(cap) == null) {
                System.out.println("❌ Missing required capability: " + cap);
                valid = false;
            } else {
                System.out.println("✅ " + cap + " = " + caps.getCapability(cap));
            }
        }
        
        if (valid) {
            System.out.println("✅ All required capabilities present");
        } else {
            System.out.println("❌ Some required capabilities missing");
        }
        
        return valid;
    }
    
    /**
     * Print capabilities in JSON format for easy copying
     */
    public static void printCapabilitiesAsJSON(DesiredCapabilities caps) {
        System.out.println("\n📋 CAPABILITIES AS JSON:");
        System.out.println("{");
        
        Map<String, Object> capMap = caps.asMap();
        int count = 0;
        int total = capMap.size();
        
        for (Map.Entry<String, Object> entry : capMap.entrySet()) {
            count++;
            String key = entry.getKey();
            Object value = entry.getValue();
            
            if (value instanceof String) {
                System.out.print("  \"" + key + "\": \"" + value + "\"");
            } else if (value instanceof Boolean) {
                System.out.print("  \"" + key + "\": " + value);
            } else if (value instanceof Number) {
                System.out.print("  \"" + key + "\": " + value);
            } else {
                System.out.print("  \"" + key + "\": \"" + value + "\"");
            }
            
            if (count < total) {
                System.out.println(",");
            } else {
                System.out.println();
            }
        }
        
        System.out.println("}");
    }
}
