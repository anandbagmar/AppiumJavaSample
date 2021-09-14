package com.eot.sample.android;

import io.appium.java_client.*;
import io.appium.java_client.android.*;
import io.appium.java_client.remote.*;
import org.openqa.selenium.remote.*;
import org.testng.*;
import org.testng.annotations.*;

import java.lang.reflect.*;
import java.net.*;
import java.util.*;

public class Appium_Native_Android_MessagesTest {

    private AppiumDriver driver;

    @BeforeMethod
    public void beforeMethod(Method method) {
        String methodName = method.getName();
        String appiumPort = "4723";
        String udid = "emulator-5554";
        Integer systemPort = 8201;
        log(String.format("Runnng test on %s, appiumPort - %s", udid, appiumPort));
        driver = createAppiumDriver(appiumPort, udid);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (null != driver) {
            log("Close the driver");
            driver.quit();
        }
    }

    @Test
    public void runMessagesTest() {
        try {
            driver.findElementById("com.google.android.apps.messaging:id/conversation_list_google_tos_popup_positive_button").click();
            driver.findElementById("android:id/button2").click();
            driver.findElementById("android:id/button1").click();
        } catch (Exception e) {
            System.out.println("Agree button not seen");
        }
        driver.findElementByAccessibilityId("Start chat").click();
        driver.findElementByAccessibilityId("Switch between entering text and numbers").click();
        driver.findElementById("com.google.android.apps.messaging:id/recipient_text_view").sendKeys("anand");
        waitFor(5);
    }

    private void waitFor(int numberOfSeconds) {
        try {
            log("Sleep for " + numberOfSeconds);
            Thread.sleep(numberOfSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void log(String message) {
        System.out.println(" ### " + new Date().toString() + " ### " + message);
    }

    private AppiumDriver createAppiumDriver(String appiumPort, String udid) {
        String APPIUM_SERVER_URL = "http://localhost:port/wd/hub";

        log(String.format("Create AppiumDriver for - %s, appiumPort - %s", udid, appiumPort));

        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12");
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.google.android.apps.messaging");
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.android.apps.messaging.ui.ConversationListActivity");
            capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
            capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
            AppiumDriver<AndroidElement> appiumDriver = new AppiumDriver<>(new URL(APPIUM_SERVER_URL.replace("port", appiumPort)), capabilities);
            log(String.format("Created AppiumDriver for - %s, appiumPort - %s", udid, appiumPort));
            return appiumDriver;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in creating Appium Driver");
        }
    }
}