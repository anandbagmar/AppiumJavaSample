package com.eot.sample.android;

import com.eot.sample.Hooks;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class FirstAppiumTest
        extends Hooks {
    private AppiumDriver driver;

    @Test
    public void runMessagesTest() {
        // 1. Create a AppiumDriver
        // 1.1 Set the capabilities of the driver

        // Appium 1.x
        // DesiredCapabilities capabilities = new DesiredCapabilities();

        // Appium 2.x
        UiAutomator2Options capabilities = new UiAutomator2Options();

        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                "UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
                "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,
                "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                "12");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                "com.google.android.apps.messaging");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                "com.google.android.apps.messaging.ui.ConversationListActivity");
        capabilities.setCapability(MobileCapabilityType.NO_RESET,
                false);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET,
                false);
        driver = new AppiumDriver(getAppiumServerUrl(),
                capabilities);
        System.out.println("Created AppiumDriver");

        // 2. Orchestrate the test scenario
        try {
            driver.findElement(By.id("com.google.android.apps.messaging:id/conversation_list_google_tos_popup_positive_button"))
                    .click();
            driver.findElement(By.id("android:id/button2"))
                    .click();
            driver.findElement(By.id("android:id/button1"))
                    .click();
        } catch (Exception e) {
            System.out.println("Agree button not seen");
        }
        driver.findElement(AppiumBy.accessibilityId("Start chat"))
                .click();
        driver.findElement(AppiumBy.accessibilityId("Switch between entering text and numbers"))
                .click();
        driver.findElement(AppiumBy.accessibilityId("com.google.android.apps.messaging:id/recipient_text_view"))
                .sendKeys("anand");
        waitFor(5);
        if (null != driver) {
            System.out.println("Close the driver");
            driver.quit();
        }
    }

    private void waitFor(int numberOfSeconds) {
        try {
            System.out.println("Sleep for " + numberOfSeconds);
            Thread.sleep(numberOfSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
