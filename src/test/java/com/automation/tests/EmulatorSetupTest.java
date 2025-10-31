package com.automation.tests;

import com.automation.utils.EmulatorHelper;
import org.testng.annotations.Test;

/**
 * Test class to help users set up and verify Android Emulator
 * Run this before using emulator execution type
 */
public class EmulatorSetupTest {

    @Test(priority = 1, description = "Check Android SDK availability")
    public void checkAndroidSDK() {
        System.out.println("🔍 Checking Android SDK...");
        
        if (EmulatorHelper.isAndroidSDKAvailable()) {
            System.out.println("✅ Android SDK is available");
        } else {
            System.out.println("❌ Android SDK not found");
            System.out.println("Please install Android Studio and add it to PATH");
        }
    }

    @Test(priority = 2, description = "List available AVDs")
    public void listAVDs() {
        System.out.println("\n🔍 Checking available AVDs...");
        EmulatorHelper.listAvailableAVDs();
    }

    @Test(priority = 3, description = "Check if recommended AVD exists")
    public void checkRecommendedAVD() {
        System.out.println("\n🔍 Checking for recommended AVD...");
        String recommendedAVD = "Pixel_7_API_33";
        
        if (EmulatorHelper.isAVDAvailable(recommendedAVD)) {
            System.out.println("✅ Recommended AVD '" + recommendedAVD + "' is available");
        } else {
            System.out.println("❌ Recommended AVD '" + recommendedAVD + "' not found");
            EmulatorHelper.printEmulatorSetupInstructions();
        }
    }

    @Test(priority = 4, description = "List connected devices")
    public void listConnectedDevices() {
        System.out.println("\n🔍 Checking connected devices...");
        EmulatorHelper.listConnectedDevices();
    }

    @Test(priority = 5, description = "Check if emulator is running", enabled = false)
    public void checkEmulatorStatus() {
        System.out.println("\n🔍 Checking emulator status...");
        
        if (EmulatorHelper.isEmulatorRunning()) {
            System.out.println("✅ Emulator is running");
        } else {
            System.out.println("❌ No emulator running");
            System.out.println("Start emulator manually or it will auto-start during test execution");
        }
    }

    @Test(priority = 6, description = "Start emulator (optional)", enabled = false)
    public void startEmulator() {
        System.out.println("\n🚀 Starting emulator...");
        String avdName = "Pixel_7_API_33";
        
        EmulatorHelper.startEmulator(avdName);
    }

    @Test(priority = 7, description = "Print setup summary")
    public void printSetupSummary() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("EMULATOR SETUP SUMMARY");
        System.out.println("=".repeat(50));
        
        System.out.println("\n✅ READY TO USE EMULATOR:");
        System.out.println("   run-tests-enhanced.bat android emulator");
        
        System.out.println("\n📋 TROUBLESHOOTING:");
        System.out.println("   - If AVD missing: Create in Android Studio AVD Manager");
        System.out.println("   - If emulator slow: Allocate more RAM in AVD settings");
        System.out.println("   - If connection fails: Restart adb with 'adb kill-server && adb start-server'");
        
        System.out.println("\n🔧 USEFUL COMMANDS:");
        System.out.println("   - List AVDs: emulator -list-avds");
        System.out.println("   - Start emulator: emulator -avd Pixel_7_API_33");
        System.out.println("   - Check devices: adb devices");
        System.out.println("   - Kill emulator: adb emu kill");
        
        System.out.println("\n" + "=".repeat(50));
    }
}
