package com.eot.sample.ios;

import io.appium.java_client.*;
import io.appium.java_client.remote.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;
import org.testng.*;
import org.testng.annotations.*;

import java.lang.reflect.*;
import java.net.*;

public class Appium_Web_iOS_HelloWorld {
    private static final String UDID = "F2D71DA6-ABD3-4311-A694-349FD64A5E7D";
    private static final String DEVICE_NAME = "iPhone 12 Pro Max";
    private static final String PLATFORM_VERSION = "14.5";
    private AppiumDriver driver;

    @BeforeMethod
    public void beforeMethod(Method method) {
        driver = createAppiumDriver();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (null != driver) {
            System.out.println("Close the driver");
            driver.quit();
        }
    }

    @Test
    public void runIOSNativeAppTest() throws InterruptedException {
        driver.get("https://google.com");
        Thread.sleep(10000);
    }

    private static AppiumDriver<WebElement> createAppiumDriver() {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        dc.setCapability(MobileCapabilityType.UDID, UDID);
        dc.setCapability(MobileCapabilityType.BROWSER_NAME, "safari");
        dc.setCapability(MobileCapabilityType.APP, "io.appium.SafariLauncher");

        AppiumDriver<WebElement> driver = null;
        try {
            driver = new AppiumDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), dc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create driver: ", e);
        }
        return driver;
    }
}
