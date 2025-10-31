package com.automation.hooks;

import com.automation.utils.EmulatorHelper;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;

/**
 * Suite-level hooks that run before and after all tests
 * Handles emulator lifecycle management
 */
public class SuiteHooks {

    @BeforeAll
    public static void beforeAllTests() {
        System.out.println("🚀 Starting test suite execution...");
        
        // Get execution type to determine if we're using emulator
        String executionType = System.getProperty("execution.type", "emulator");
        
        if ("emulator".equalsIgnoreCase(executionType)) {
            System.out.println("📱 Emulator execution detected");
            // Note: Individual tests will start their own emulator instances
            // This is just for logging and preparation
        }
    }

    @AfterAll
    public static void afterAllTests() {
        System.out.println("🏁 Test suite execution completed");
        
        // Get execution type to determine if we need to close emulator
        String executionType = System.getProperty("execution.type", "emulator");
        
        if ("emulator".equalsIgnoreCase(executionType)) {
            System.out.println("🔄 Cleaning up emulator instances...");
            
            // Close all running emulators
            EmulatorHelper.closeAllEmulators();
            
            System.out.println("✅ Emulator cleanup completed");
        } else {
            System.out.println("ℹ️ No emulator cleanup needed for execution type: " + executionType);
        }
        
        System.out.println("🎉 All tests completed and cleanup finished!");
    }
}
