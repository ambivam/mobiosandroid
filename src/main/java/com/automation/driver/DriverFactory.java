package com.automation.driver;

import com.automation.config.ConfigManager;
import com.automation.utils.CapabilityPrinter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class DriverFactory {
    private static AppiumDriver driver;

    public static void createDriver(String platform, String executionType) {
        try {
            ConfigManager config = new ConfigManager();
            
            System.out.println("Creating driver for platform: " + platform + ", execution: " + executionType);
            
            if ("browserstack".equalsIgnoreCase(executionType)) {
                driver = createBrowserStackDriver(platform, config);
            } else if ("perfecto".equalsIgnoreCase(executionType)) {
                driver = createPerfectoDriver(platform, config);
            } else if ("emulator".equalsIgnoreCase(executionType)) {
                driver = createEmulatorDriver(platform, config);
            } else {
                driver = createLocalDriver(platform, config);
            }
            
            // Set implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            System.out.println("✓ Driver created successfully for: " + platform + " on " + executionType);
            
        } catch (Exception e) {
            System.out.println("✗ Failed to create driver: " + e.getMessage());
            throw new RuntimeException("Failed to create driver: " + e.getMessage(), e);
        }
    }

    private static AppiumDriver createLocalDriver(String platform, ConfigManager config) throws Exception {
        config.loadConfig(platform);
        DesiredCapabilities caps = new DesiredCapabilities();
        String serverUrl = config.getProperty("appium.server.url");
        
        if ("android".equalsIgnoreCase(platform)) {
            caps.setCapability("platformName", "Android");
            caps.setCapability("platformVersion", config.getProperty("platform.version"));
            caps.setCapability("deviceName", config.getProperty("device.name"));
            caps.setCapability("automationName", "UiAutomator2");
            caps.setCapability("appPackage", config.getProperty("app.package"));
            caps.setCapability("appActivity", config.getProperty("app.activity"));
            return new AndroidDriver(new URL(serverUrl), caps);
            
        } else if ("ios".equalsIgnoreCase(platform)) {
            caps.setCapability("platformName", "iOS");
            caps.setCapability("platformVersion", config.getProperty("platform.version"));
            caps.setCapability("deviceName", config.getProperty("device.name"));
            caps.setCapability("automationName", "XCUITest");
            caps.setCapability("bundleId", config.getProperty("bundle.id"));
            return new IOSDriver(new URL(serverUrl), caps);
        }
        
        throw new RuntimeException("Unsupported platform: " + platform);
    }

    private static AppiumDriver createEmulatorDriver(String platform, ConfigManager config) throws Exception {
        config.loadConfig(platform + "-emulator");
        DesiredCapabilities caps = new DesiredCapabilities();
        String serverUrl = config.getProperty("appium.server.url");
        
        if ("android".equalsIgnoreCase(platform)) {
            // Core capabilities using Appium 2.0 format
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:udid", config.getProperty("device.udid", "emulator-5554"));
            caps.setCapability("appium:deviceName", "Android");
            
            // Platform and app capabilities
            caps.setCapability("appium:platformVersion", config.getProperty("platform.version"));
            caps.setCapability("appium:appPackage", config.getProperty("app.package"));
            
            // Only set activity if it's specified (not commented out)
            try {
                String activity = config.getProperty("app.activity");
                if (activity != null && !activity.trim().isEmpty()) {
                    caps.setCapability("appium:appActivity", activity);
                }
            } catch (RuntimeException e) {
                // Activity not specified, let Appium auto-detect
                System.out.println("⚠️ No app.activity specified, Appium will auto-detect the main activity");
            }
            
            // Emulator specific capabilities
            caps.setCapability("appium:avd", config.getProperty("avd.name"));
            caps.setCapability("appium:noReset", Boolean.parseBoolean(config.getProperty("no.reset", "false")));
            caps.setCapability("appium:fullReset", Boolean.parseBoolean(config.getProperty("full.reset", "false")));
            caps.setCapability("appium:autoGrantPermissions", Boolean.parseBoolean(config.getProperty("auto.grant.permissions", "true")));
            caps.setCapability("appium:autoAcceptAlerts", Boolean.parseBoolean(config.getProperty("auto.accept.alerts", "true")));
            caps.setCapability("appium:newCommandTimeout", Integer.parseInt(config.getProperty("new.command.timeout", "300")));
            
            // Performance settings
            caps.setCapability("appium:appWaitTimeout", Integer.parseInt(config.getProperty("app.wait.timeout", "30000")));
            caps.setCapability("appium:appWaitActivity", config.getProperty("app.wait.activity", "*"));
            
            // App path
            String appPath = config.getProperty("app.path", "");
            if (!appPath.isEmpty()) {
                caps.setCapability("appium:app", System.getProperty("user.dir") + "/" + appPath);
            }
            
            // Print detailed capabilities for debugging
            CapabilityPrinter.printCapabilities(caps, "emulator");
            CapabilityPrinter.validateEmulatorCapabilities(caps);
            
            System.out.println("🚀 Starting Android Emulator...");
            return new AndroidDriver(new URL(serverUrl), caps);
            
        } else if ("ios".equalsIgnoreCase(platform)) {
            // iOS Simulator support can be added here
            caps.setCapability("platformName", "iOS");
            caps.setCapability("platformVersion", config.getProperty("platform.version"));
            caps.setCapability("deviceName", config.getProperty("device.name"));
            caps.setCapability("automationName", "XCUITest");
            caps.setCapability("bundleId", config.getProperty("bundle.id"));
            return new IOSDriver(new URL(serverUrl), caps);
        }
        
        throw new RuntimeException("Unsupported platform for emulator: " + platform);
    }

    private static AppiumDriver createBrowserStackDriver(String platform, ConfigManager config) throws Exception {
        config.loadConfig("browserstack");
        DesiredCapabilities caps = new DesiredCapabilities();
        
        // BrowserStack credentials
        caps.setCapability("browserstack.user", config.getProperty("browserstack.username"));
        caps.setCapability("browserstack.key", config.getProperty("browserstack.access.key"));
        caps.setCapability("project", config.getProperty("browserstack.project"));
        caps.setCapability("build", config.getProperty("browserstack.build"));
        
        String browserStackUrl = config.getProperty("browserstack.url");
        
        if ("android".equalsIgnoreCase(platform)) {
            caps.setCapability("platformName", "Android");
            caps.setCapability("platformVersion", config.getProperty("browserstack.android.os.version"));
            caps.setCapability("deviceName", config.getProperty("browserstack.android.device"));
            caps.setCapability("app", config.getProperty("browserstack.android.app.url"));
            return new AndroidDriver(new URL(browserStackUrl), caps);
            
        } else if ("ios".equalsIgnoreCase(platform)) {
            caps.setCapability("platformName", "iOS");
            caps.setCapability("platformVersion", config.getProperty("browserstack.ios.os.version"));
            caps.setCapability("deviceName", config.getProperty("browserstack.ios.device"));
            caps.setCapability("app", config.getProperty("browserstack.ios.app.url"));
            return new IOSDriver(new URL(browserStackUrl), caps);
        }
        
        throw new RuntimeException("Unsupported platform: " + platform);
    }

    private static AppiumDriver createPerfectoDriver(String platform, ConfigManager config) throws Exception {
        config.loadConfig("perfecto");
        
        // Get Perfecto URL from properties (supports variable substitution)
        String perfectoUrl = config.getProperty("perfecto.url");
        
        // Log the exact URL being used for debugging
        System.out.println("🔗 Perfecto Hub URL: " + perfectoUrl);
        System.out.println("🔑 Security Token: " + config.getProperty("perfecto.security.token").substring(0, 20) + "...");
        
        if ("android".equalsIgnoreCase(platform)) {
            // Modern Android Perfecto capabilities using UiAutomator2Options
            UiAutomator2Options options = new UiAutomator2Options();
            
            // Standard W3C capabilities (no namespace prefix)
            options.setCapability("platformName", "Android");
            options.setCapability("appium:automationName", "UiAutomator2");
            options.setCapability("appium:deviceName", config.getProperty("perfecto.android.device.id"));
            options.setCapability("appium:platformVersion", config.getProperty("perfecto.android.os.version"));
            options.setCapability("appium:app", config.getProperty("perfecto.android.app.path"));
            options.setCapability("appium:autoLaunch", true);
            options.setCapability("appium:screenshotOnError", true);
            options.setCapability("appium:takesScreenshot", true);
            options.setCapability("appium:autoInstrumentation", true);
            options.setCapability("appium:appPackage", config.getProperty("perfecto.android.app.package"));
            
            // Perfecto-specific capabilities with perfecto: namespace
            options.setCapability("perfecto:securityToken", config.getProperty("perfecto.security.token"));
            options.setCapability("perfecto:projectName", config.getProperty("perfecto.project"));
            options.setCapability("perfecto:projectVersion", config.getProperty("perfecto.build"));
            options.setCapability("perfecto:report.video", Boolean.parseBoolean(config.getProperty("perfecto.video", "true")));
            options.setCapability("perfecto:report.debug", Boolean.parseBoolean(config.getProperty("perfecto.debug", "true")));
            
            System.out.println("🚀 Starting Android test on Perfecto Cloud with modern capabilities...");
            return new AndroidDriver(new URL(perfectoUrl), options);
            
        } else if ("ios".equalsIgnoreCase(platform)) {
            // Modern iOS Perfecto capabilities using XCUITestOptions
            XCUITestOptions options = new XCUITestOptions();
            
            // Standard W3C capabilities (no namespace prefix)
            options.setCapability("platformName", "iOS");
            options.setCapability("appium:automationName", "Appium");
            options.setCapability("appium:deviceName", config.getProperty("perfecto.ios.device.id"));
            options.setCapability("appium:platformVersion", config.getProperty("perfecto.ios.os.version"));
            options.setCapability("manufacturer", config.getProperty("perfecto.ios.manufacturer"));
            options.setCapability("model", config.getProperty("perfecto.ios.device.model"));
            options.setCapability("appium:app", config.getProperty("perfecto.ios.app.path"));
            options.setCapability("appium:autoLaunch", true);
            options.setCapability("appium:screenshotOnError", true);
            options.setCapability("appium:takesScreenshot", true);
            options.setCapability("appium:bundleId", config.getProperty("perfecto.ios.bundle.id"));
            
            // Perfecto-specific capabilities with perfecto: namespace
            options.setCapability("perfecto:securityToken", config.getProperty("perfecto.security.token"));
            options.setCapability("perfecto:projectName", config.getProperty("perfecto.project"));
            options.setCapability("perfecto:projectVersion", config.getProperty("perfecto.build"));
            options.setCapability("perfecto:report.video", Boolean.parseBoolean(config.getProperty("perfecto.video", "true")));
            options.setCapability("perfecto:report.debug", Boolean.parseBoolean(config.getProperty("perfecto.debug", "true")));
            
            System.out.println("🚀 Starting iOS test on Perfecto Cloud with modern capabilities...");
            return new IOSDriver(new URL(perfectoUrl), options);
        }
        
        throw new RuntimeException("Unsupported platform for Perfecto: " + platform);
    }

    public static AppiumDriver getDriver() {
        if (driver == null) {
            throw new RuntimeException("Driver not initialized. Call createDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println("Driver quit successfully");
        }
    }
}
