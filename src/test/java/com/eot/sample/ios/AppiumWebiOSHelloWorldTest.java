package com.eot.sample.ios;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Date;

import static com.eot.sample.Hooks.*;

public class AppiumWebiOSHelloWorldTest {
    private static final String UDID = "F2D71DA6-ABD3-4311-A694-349FD64A5E7D";
    private static final String DEVICE_NAME = "iPhone 12 Pro Max";
    private static final String PLATFORM_VERSION = "14.5";
    private AppiumDriver driver;

    @BeforeSuite
    public void beforeAll() {
        startAppiumServer();
    }

    @AfterSuite
    public void afterAll() {
        stopAppiumServer();
    }

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
    public void runIOSWebTest() throws InterruptedException {
        System.out.println("Start time: " + new Date());
        Thread.sleep(3000);
        driver.get("https://applitools.com/helloworld");
        for (int stepNumber = 0; stepNumber < 5; stepNumber++) {
            driver.findElement(By.linkText("?diff1")).click();
            Thread.sleep(1000);
        }

        driver.findElement(By.tagName("button")).click();
        driver.quit();

        System.out.println("End time: " + new Date());
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
        dc.setCapability(MobileCapabilityType.BROWSER_NAME, "safari");
        dc.setCapability(MobileCapabilityType.APP, "io.appium.SafariLauncher");

        return new AppiumDriver(getAppiumServerUrl(), dc);
    }
}
