package com.eot.sample.android;

import io.appium.java_client.*;
import io.appium.java_client.remote.*;
import org.openqa.selenium.remote.*;
import org.testng.annotations.*;

import java.net.*;

public class FirstAppiumTest {
    private AppiumDriver driver;

    @Test
    public void runMessagesTest() {
        String APPIUM_SERVER_URL = "http://localhost:4723/wd/hub";
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
            driver = new AppiumDriver<>(new URL(APPIUM_SERVER_URL), capabilities);
            System.out.println("Created AppiumDriver");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in creating Appium Driver");
        }
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
