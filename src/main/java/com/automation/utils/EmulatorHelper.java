package com.automation.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Utility class to help with Android Emulator operations
 * Useful for 4-5 years experienced automation developers
 */
public class EmulatorHelper {

    /**
     * Check if Android SDK is available
     */
    public static boolean isAndroidSDKAvailable() {
        try {
            Process process = Runtime.getRuntime().exec("adb version");
            process.waitFor();
            return process.exitValue() == 0;
        } catch (Exception e) {
            System.out.println("Android SDK not found. Please install Android Studio.");
            return false;
        }
    }

    /**
     * List all available AVDs
     */
    public static void listAvailableAVDs() {
        System.out.println("=== AVAILABLE ANDROID VIRTUAL DEVICES ===");
        try {
            Process process = Runtime.getRuntime().exec("emulator -list-avds");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            String line;
            boolean hasAVDs = false;
            while ((line = reader.readLine()) != null) {
                System.out.println("📱 " + line);
                hasAVDs = true;
            }
            
            if (!hasAVDs) {
                System.out.println("❌ No AVDs found!");
                System.out.println("Create an AVD using Android Studio:");
                System.out.println("1. Open Android Studio");
                System.out.println("2. Tools > AVD Manager");
                System.out.println("3. Create Virtual Device");
                System.out.println("4. Choose Pixel 7, API 33 (recommended)");
            }
            
        } catch (Exception e) {
            System.out.println("Error listing AVDs: " + e.getMessage());
            System.out.println("Make sure Android Studio is installed and emulator is in PATH");
        }
    }

    /**
     * Check if specific AVD exists
     */
    public static boolean isAVDAvailable(String avdName) {
        try {
            Process process = Runtime.getRuntime().exec("emulator -list-avds");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals(avdName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Start an emulator if not already running
     */
    public static void startEmulator(String avdName) {
        System.out.println("Starting emulator: " + avdName);
        
        if (!isAVDAvailable(avdName)) {
            System.out.println("❌ AVD '" + avdName + "' not found!");
            listAvailableAVDs();
            return;
        }

        try {
            // Check if emulator is already running
            if (isEmulatorRunning()) {
                System.out.println("✅ Emulator is already running");
                return;
            }

            System.out.println("🚀 Starting emulator (this may take a few minutes)...");
            
            // Start emulator in background
            ProcessBuilder pb = new ProcessBuilder("emulator", "-avd", avdName, "-no-snapshot-save");
            pb.start();
            
            // Wait for emulator to boot
            System.out.println("⏳ Waiting for emulator to boot...");
            waitForEmulatorBoot();
            
        } catch (Exception e) {
            System.out.println("❌ Failed to start emulator: " + e.getMessage());
        }
    }

    /**
     * Check if any emulator is currently running
     */
    public static boolean isEmulatorRunning() {
        try {
            Process process = Runtime.getRuntime().exec("adb devices");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("emulator") && line.contains("device")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for emulator to fully boot
     */
    private static void waitForEmulatorBoot() {
        int maxWaitTime = 120; // 2 minutes
        int waitTime = 0;
        
        while (waitTime < maxWaitTime) {
            try {
                Process process = Runtime.getRuntime().exec("adb shell getprop sys.boot_completed");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String result = reader.readLine();
                
                if ("1".equals(result)) {
                    System.out.println("✅ Emulator booted successfully!");
                    return;
                }
                
                Thread.sleep(5000); // Wait 5 seconds
                waitTime += 5;
                System.out.println("⏳ Still booting... (" + waitTime + "s)");
                
            } catch (Exception e) {
                // Continue waiting
            }
        }
        
        System.out.println("⚠️ Emulator boot timeout. It may still be starting...");
    }

    /**
     * Get connected devices and emulators
     */
    public static void listConnectedDevices() {
        System.out.println("=== CONNECTED DEVICES ===");
        try {
            Process process = Runtime.getRuntime().exec("adb devices");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            String line;
            boolean hasDevices = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("device") && !line.contains("List of devices")) {
                    if (line.contains("emulator")) {
                        System.out.println("📱 " + line + " (Emulator)");
                    } else {
                        System.out.println("📱 " + line + " (Physical Device)");
                    }
                    hasDevices = true;
                }
            }
            
            if (!hasDevices) {
                System.out.println("❌ No devices connected!");
                System.out.println("For physical device: Connect via USB and enable USB debugging");
                System.out.println("For emulator: Start an AVD from Android Studio");
            }
            
        } catch (Exception e) {
            System.out.println("Error listing devices: " + e.getMessage());
        }
    }

    /**
     * Provide setup instructions for emulator
     */
    public static void printEmulatorSetupInstructions() {
        System.out.println("\n=== ANDROID EMULATOR SETUP GUIDE ===");
        System.out.println("1. Install Android Studio from: https://developer.android.com/studio");
        System.out.println("2. Open Android Studio");
        System.out.println("3. Go to Tools > AVD Manager");
        System.out.println("4. Click 'Create Virtual Device'");
        System.out.println("5. Choose 'Pixel 7' as device");
        System.out.println("6. Select API Level 33 (Android 13)");
        System.out.println("7. Name it 'Pixel_7_API_33'");
        System.out.println("8. Click 'Finish'");
        System.out.println("9. Start the emulator from AVD Manager");
        System.out.println("\nAlternatively, use command line:");
        System.out.println("emulator -avd Pixel_7_API_33");
        System.out.println("=====================================\n");
    }
}
