package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CommonUtils {
    private static final Logger logger = LogManager.getLogger(CommonUtils.class);

    public static void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                logger.info("Directory created: {}", directoryPath);
            } else {
                logger.error("Failed to create directory: {}", directoryPath);
            }
        }
    }

    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }

    public static String getCurrentTimestamp(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            logger.info("Waited for {} seconds", seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Wait interrupted", e);
        }
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        
        return sb.toString();
    }

    public static String generateRandomEmail() {
        return "test_" + generateRandomString(8) + "@example.com";
    }

    public static boolean isStringEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static void cleanupDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        boolean deleted = file.delete();
                        if (deleted) {
                            logger.info("Deleted file: {}", file.getName());
                        }
                    }
                }
            }
        }
    }
}
