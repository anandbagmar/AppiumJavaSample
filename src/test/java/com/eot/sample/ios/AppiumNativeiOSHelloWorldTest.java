package com.eot.sample.ios;

import com.eot.sample.Hooks;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class AppiumNativeiOSHelloWorldTest
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
        if(null != driver) {
            System.out.println("Close the driver");
            driver.quit();
        }
    }

    @Test
    public void runIOSNativeAppTest() {
        driver.findElement(AppiumBy.accessibilityId("Make the number below random."))
              .click();
        driver.findElement(AppiumBy.accessibilityId("MakeRandomNumberCheckbox"))
              .click();
        driver.findElement(AppiumBy.accessibilityId("SimulateDiffsCheckbox"))
              .click();
        driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\"Click me!\"]"))
              .click();
    }

    private AppiumDriver createAppiumDriver() {
        // Appium 1.x
        // DesiredCapabilities dc = new DesiredCapabilities();

        // Appium 2.x
        XCUITestOptions dc = new XCUITestOptions();

        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        dc.setCapability(MobileCapabilityType.UDID, UDID);
        dc.setCapability("app", System.getProperty("user.dir") + "/src/test/resources/sampleApps/eyes-ios-hello-world.zip");

        AppiumDriver driver = null;
        driver = new AppiumDriver(getAppiumServerUrl(), dc);
        return driver;
    }
}
