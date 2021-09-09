package com.eot.utils;

import io.github.bonigarcia.wdm.*;
import org.apache.commons.lang3.*;

import java.io.*;
import java.util.*;

public class DriverUtils {
    public static String getPathForChromeDriverFromMachine() {
        WebDriverManager.chromedriver().setup();
        String chromeDriverPath = WebDriverManager.chromedriver().getDownloadedDriverPath();
        System.out.println("ChromeDriver path: " + chromeDriverPath);
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        return chromeDriverPath;
    }

    public static void setChromeDriverForConnectedDevice() {
        int[] versionNamesArr = getChromeVersionsFor();
        if (versionNamesArr.length > 0) {
            int highestChromeVersion = Arrays.stream(versionNamesArr).max().getAsInt();
            String message = "Setting up ChromeDriver for Chrome version " + highestChromeVersion + " on device";
            System.out.println(message);
            WebDriverManager.chromedriver().browserVersion(String.valueOf(highestChromeVersion)).setup();
            String chromeDriverPath = WebDriverManager.chromedriver().getDownloadedDriverPath();
        } else {
            throw new RuntimeException("No devices connected");
        }
    }

    private static int[] getChromeVersionsFor() {
        CommandPrompt cmd = new CommandPrompt();
        String resultStdOut = null;
        resultStdOut = getChromeVersionsOnDevice(cmd);
        int[] versionNamesArr = {};
        if (resultStdOut.contains("versionName=")) {
            String[] foundVersions = resultStdOut.split("\n");
            for (String foundVersion : foundVersions) {
                String version = foundVersion.split("=")[1].split("\\.")[0];
                String format = String.format("Found Chrome version - '%s' on device", version);
                System.out.println(format);
                versionNamesArr = ArrayUtils.add(versionNamesArr, Integer.parseInt(version));
            }
        } else {
            System.out.println(String.format("Chrome not found on device"));
        }
        return versionNamesArr;
    }

    private static String getChromeVersionsOnDevice(CommandPrompt cmd) {
        String resultStdOut;
        try {
            resultStdOut = cmd.runCommandThruProcess("adb shell dumpsys package com.android.chrome | grep versionName");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting chrome version from device - " + e.getMessage());
        }
        return resultStdOut;
    }

    public static String getPathForFirefoxDriverFromMachine() {
        WebDriverManager.firefoxdriver().setup();
        String firefoxDriverPath = WebDriverManager.firefoxdriver().getDownloadedDriverPath();
        System.out.println("FirefoxDriver path: " + firefoxDriverPath);
        System.setProperty("webdriver.firefox.driver", firefoxDriverPath);
        return firefoxDriverPath;
    }
}
