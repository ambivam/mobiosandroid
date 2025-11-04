package com.perfecto.sample;

/**
 * Configuration class to handle platform-specific settings for Android and iOS
 * This makes it easy for automation engineers to understand and modify platform settings
 */
public class PlatformConfig {
    
    // Platform constants
    public static final String ANDROID = "Android";
    public static final String IOS = "iOS";
    
    // App paths - Update these paths according to your app location
    public static final String ANDROID_APP_PATH = "PRIVATE:apps/app-V1.14.10-QA.apk";
    public static final String IOS_APP_PATH = "PRIVATE:apps/eStratis.ipa";
    
    // App identifiers - Update these according to your app's package/bundle ID
    public static final String ANDROID_APP_PACKAGE = "com.yourcompany.yourapp";
    public static final String IOS_BUNDLE_ID = "com.yourcompany.yourapp";
    
    // Device models - You can modify these patterns to target specific devices
    public static final String ANDROID_DEVICE_MODEL = "Galaxy S.*|LG.*|Pixel.*";
    public static final String IOS_DEVICE_MODEL = "iPhone.*";
    
    // Test credentials - Update these with your test account credentials
    public static final String TEST_EMAIL = "test@perfecto.com";
    public static final String TEST_PASSWORD = "test123";
    
    /**
     * Get app path based on platform
     * @param platform - "Android" or "iOS"
     * @return app path for the specified platform
     */
    public static String getAppPath(String platform) {
        if (ANDROID.equalsIgnoreCase(platform)) {
            return ANDROID_APP_PATH;
        } else if (IOS.equalsIgnoreCase(platform)) {
            return IOS_APP_PATH;
        }
        throw new IllegalArgumentException("Unsupported platform: " + platform);
    }
    
    /**
     * Get app identifier based on platform
     * @param platform - "Android" or "iOS"
     * @return app identifier for the specified platform
     */
    public static String getAppIdentifier(String platform) {
        if (ANDROID.equalsIgnoreCase(platform)) {
            return ANDROID_APP_PACKAGE;
        } else if (IOS.equalsIgnoreCase(platform)) {
            return IOS_BUNDLE_ID;
        }
        throw new IllegalArgumentException("Unsupported platform: " + platform);
    }
    
    /**
     * Get device model pattern based on platform
     * @param platform - "Android" or "iOS"
     * @return device model pattern for the specified platform
     */
    public static String getDeviceModel(String platform) {
        if (ANDROID.equalsIgnoreCase(platform)) {
            return ANDROID_DEVICE_MODEL;
        } else if (IOS.equalsIgnoreCase(platform)) {
            return IOS_DEVICE_MODEL;
        }
        throw new IllegalArgumentException("Unsupported platform: " + platform);
    }
}
