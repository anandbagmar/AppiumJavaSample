package com.eot.sample.android;

import com.eot.sample.Hooks;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Date;

public class AppiumNativeAndroidMessagesTest
        extends Hooks {

    private AppiumDriver driver;

    @BeforeMethod
    public void beforeMethod(Method method) {
        String methodName = method.getName();
        String udid = "emulator-5554";
        log(String.format("Running test on '%s'", udid));
        driver = createAppiumDriver(getAppiumServerUrl(), udid);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if(null != driver) {
            log("Close the driver");
            driver.quit();
        }
    }

    @Test
    public void runMessagesTest() {
        try {
            driver.findElement(By.id("com.google.android.apps.messaging:id/conversation_list_google_tos_popup_positive_button"))
                  .click();
            driver.findElement(By.id("android:id/button2"))
                  .click();
            driver.findElement(By.id("android:id/button1"))
                  .click();
        } catch(Exception e) {
            System.out.println("Agree button not seen");
        }
        driver.findElement(AppiumBy.accessibilityId("Start chat"))
              .click();
        driver.findElement(AppiumBy.accessibilityId("Switch between entering text and numbers"))
              .click();
        driver.findElement(By.id("com.google.android.apps.messaging:id/recipient_text_view"))
              .sendKeys("anand");
        waitFor(5);
    }

    private void waitFor(int numberOfSeconds) {
        try {
            log("Sleep for " + numberOfSeconds);
            Thread.sleep(numberOfSeconds * 1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void log(String message) {
        System.out.println(" ### " + new Date() + " ### " + message);
    }

    private AppiumDriver createAppiumDriver(URL appiumServerUrl, String udid) {
        log(String.format("Create AppiumDriver with appium server on: '%s', device udid - '%s'", appiumServerUrl, udid));

        // Appium 1.x
        // DesiredCapabilities capabilities = new DesiredCapabilities();

        // Appium 2.x
        UiAutomator2Options capabilities = new UiAutomator2Options();

        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.google.android.apps.messaging");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.android.apps.messaging.ui.ConversationListActivity");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        AppiumDriver appiumDriver = new AppiumDriver(appiumServerUrl, capabilities);
        log(String.format("Created AppiumDriver for - %s, appiumPort - %s", udid, appiumServerUrl));
        return appiumDriver;
    }
}