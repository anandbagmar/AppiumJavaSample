package com.eot.sample;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class CalcTest {

    private AppiumDriver driver;

    @BeforeMethod
    public void beforeMethod(Method method) {
        String methodName = method.getName();
        String appiumPort = "4723";
        String udid = "emulator-5554";
        Integer systemPort = 8201;
        log(String.format("Runnng test on %s:%d, appiumPort - %s", udid, systemPort, appiumPort));
        driver = createAppiumDriver(appiumPort, udid, systemPort);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (null != driver) {
            log("Close the driver");
            driver.quit();
        }
    }

    @Test
    public void runTest() {
        int num1 = 5;
        int num2 = 4;
        driver.findElement(By.id("digit_" + num1)).click();
        driver.findElement(By.id("op_add")).click();
        driver.findElement(By.id("digit_" + num2)).click();
    }

    private void log(String message) {
        System.out.println(" ### " + new Date().toString() + " ### " + message);
    }

    private AppiumDriver createAppiumDriver(String appiumPort, String udid, Integer systemPort) {
        String APPIUM_SERVER_URL = "http://localhost:port/wd/hub";

        log(String.format("Create AppiumDriver for - %s:%s, appiumPort - %s", udid, systemPort, appiumPort));

        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            capabilities.setCapability(MobileCapabilityType.UDID, udid);
            capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort);
            capabilities.setCapability("appPackage", "com.android.calculator2");
            capabilities.setCapability("appActivity", ".Calculator");
            capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
            AppiumDriver<AndroidElement> appiumDriver = new AppiumDriver<>(new URL(APPIUM_SERVER_URL.replace("port", appiumPort)), capabilities);
            log(String.format("Created AppiumDriver for - %s:%s, appiumPort - %s", udid, systemPort, appiumPort));
            return appiumDriver;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in creating Appium Driver");
        }
    }
}