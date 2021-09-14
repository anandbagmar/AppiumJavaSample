package com.eot.utils;

import io.github.bonigarcia.wdm.*;
import org.apache.commons.lang3.*;

import java.io.*;
import java.util.*;

public class DriverUtils {
    public static String setPathForChromeDriverFromMachine() {
        WebDriverManager.chromedriver().setup();
        String chromeDriverPath = WebDriverManager.chromedriver().getDownloadedDriverPath();
        System.out.println("ChromeDriver path: " + chromeDriverPath);
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        return chromeDriverPath;
    }

    private static void setDriverForConnectedDevice(String browserName) {
        int[] versionNamesArr = getBrowserVersionsFor(browserName);
        if (versionNamesArr.length > 0) {
            int highestBrowserVersion = Arrays.stream(versionNamesArr).max().getAsInt();
            String message = "Setting up ChromeDriver for Chrome version " + highestBrowserVersion + " on device";
            System.out.println(message);
            switch (browserName) {
                case "chrome":
                    WebDriverManager.chromedriver().browserVersion(String.valueOf(highestBrowserVersion)).setup();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().browserVersion(String.valueOf(highestBrowserVersion)).setup();
                    break;
                case "safari":
                    WebDriverManager.safaridriver().browserVersion(String.valueOf(highestBrowserVersion)).setup();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().browserVersion(String.valueOf(highestBrowserVersion)).setup();
                    break;
            }
        } else {
            throw new RuntimeException("No devices connected");
        }
    }

    public static void setChromeDriverForConnectedDevice() {
        setDriverForConnectedDevice("chrome");
    }

    public static void setFirefoxDriverForConnectedDevice() {
        setDriverForConnectedDevice("firefox");
    }

    public static void setSafariDriverForConnectedDevice() {
        setDriverForConnectedDevice("safari");
    }

    public static void setEdgeDriverForConnectedDevice() {
        setDriverForConnectedDevice("edge");
    }

    private static int[] getBrowserVersionsFor(final String browserName) {
        CommandPrompt cmd = new CommandPrompt();
        String resultStdOut = null;
        resultStdOut = getBrowserVersionsOnDevice(cmd, browserName);
        int[] versionNamesArr = {};
        if (resultStdOut.contains("versionName=")) {
            String[] foundVersions = resultStdOut.split("\n");
            for (String foundVersion : foundVersions) {
                String version = foundVersion.split("=")[1].split("\\.")[0];
                String format = String.format("Found " + browserName + " version - '%s' on device", version);
                System.out.println(format);
                versionNamesArr = ArrayUtils.add(versionNamesArr, Integer.parseInt(version));
            }
        } else {
            System.out.println(String.format(browserName + " not found on device"));
        }
        return versionNamesArr;
    }

    private static String getBrowserVersionsOnDevice(CommandPrompt cmd, final String browserName) {
        String resultStdOut;
        try {
            resultStdOut = cmd.runCommandThruProcess("adb shell dumpsys package com.android." + browserName + " | grep versionName");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting " + browserName + " version from device - " + e.getMessage());
        }
        return resultStdOut;
    }

    public static String setPathForFirefoxDriverFromMachine() {
        WebDriverManager.firefoxdriver().setup();
        String firefoxDriverPath = WebDriverManager.firefoxdriver().getDownloadedDriverPath();
        System.out.println("FirefoxDriver path: " + firefoxDriverPath);
        System.setProperty("webdriver.firefox.driver", firefoxDriverPath);
        return firefoxDriverPath;
    }
}
