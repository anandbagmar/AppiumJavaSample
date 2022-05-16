package com.eot.sample.ios;

import com.eot.sample.Hooks;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class Appium_Native_iOS_HelloWorldTest
        extends Hooks {
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
    public void runIOSNativeAppTest() {
        driver.findElementByAccessibilityId("Make the number below random.")
              .click();
        driver.findElementByAccessibilityId("MakeRandomNumberCheckbox")
              .click();
        driver.findElementByAccessibilityId("SimulateDiffsCheckbox")
              .click();
        driver.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Click me!\"]")
              .click();
    }

    private AppiumDriver<WebElement> createAppiumDriver() {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME,
                         "iOS");
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                         "XCUITest");
        dc.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                         PLATFORM_VERSION);
        dc.setCapability(MobileCapabilityType.DEVICE_NAME,
                         DEVICE_NAME);
        dc.setCapability(MobileCapabilityType.UDID,
                         UDID);
        dc.setCapability("app",
                         System.getProperty("user.dir") + "/src/test/resources/sampleApps/eyes-ios-hello-world.zip");

        AppiumDriver<WebElement> driver = null;
        driver = new AppiumDriver<>(getAppiumServerUrl(),
                                    dc);
        return driver;
    }
}
