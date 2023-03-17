package com.eot.sample.android;

import com.eot.utils.DriverUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Date;

import static com.eot.sample.Hooks.*;

public class AppiumWebAndroidHelloWorldTest {
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
        DriverUtils.setChromeDriverForConnectedDevice();
        driver = setupMobileWeb();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (null != driver) {
            System.out.println("Close the driver");
            driver.quit();
        }
    }

    @Test()
    public void appiumWebTest() throws
            InterruptedException {
        System.out.println("Start time: " + new Date());
        driver.get("https://applitools.com/helloworld");
        for (int stepNumber = 0; stepNumber < 5; stepNumber++) {
            driver.findElement(By.linkText("?diff1"))
                    .click();
            Thread.sleep(1000);
        }

        driver.findElement(By.tagName("button"))
                .click();
        System.out.println("End time: " + new Date());
    }

    private AppiumDriver setupMobileWeb() {
        // Appium 1.x
        // DesiredCapabilities capabilities = new DesiredCapabilities();

        // Appium 2.x
        UiAutomator2Options capabilities = new UiAutomator2Options();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
        capabilities.setCapability("chromedriverExecutable", System.getProperty("webdriver.chrome.driver"));

        // Open browser.
        AppiumDriver driver = null;
        driver = new AppiumDriver(getAppiumServerUrl(), capabilities);
        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(60));
        return driver;
    }
}