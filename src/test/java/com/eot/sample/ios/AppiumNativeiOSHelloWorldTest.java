package com.eot.sample.ios;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static com.eot.sample.Hooks.*;

public class AppiumNativeiOSHelloWorldTest {
    private static final String UDID = "7B0FA5D8-1926-4461-A313-243BCE78A6CE";
    private static final String DEVICE_NAME = "iPhone 14 Pro";
    private static final String PLATFORM_VERSION = "16.2";
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
        dc.setCapability("app", System.getProperty("user.dir") + "/src/test/resources/sampleApps" +
                                        "/HelloWorldiOS.app");
//        dc.setCapability("app", System.getProperty("user.dir") + "/src/test/resources/sampleApps/eyes-ios-hello-world.zip");

        return new AppiumDriver(getAppiumServerUrl(), dc);
    }
}
