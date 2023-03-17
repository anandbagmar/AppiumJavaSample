package com.eot.sample.android;

import com.eot.utils.Wait;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Date;

import static com.eot.sample.Hooks.*;

public class AppiumNativeAndroidMessagesTest {

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
        String methodName = method.getName();
        String udid = "emulator-5554";
        log(String.format("Running test '%s' on '%s'", methodName, udid));
        driver = createAppiumDriver(udid);
        handleUpgradePopup();
    }

    private void handleUpgradePopup() {
        Wait.waitFor(1);
        WebElement upgradeAppNotificationElement = driver.findElement(By.id("android:id/button1"));
        if (null != upgradeAppNotificationElement) {
            upgradeAppNotificationElement.click();
            Wait.waitFor(1);
        }
        WebElement gotItElement = driver.findElement(By.id("com.android2.calculator3:id/cling_dismiss"));
        if (null != gotItElement) {
            gotItElement.click();
            Wait.waitFor(1);
        }
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
        int p1 = 3;
        int p2 = 5;
        driver.findElement(By.id("digit" + p1))
                .click();
        driver.findElement(By.id("plus"))
                .click();
        driver.findElement(By.id("digit" + p2))
                .click();
        driver.findElement(By.id("equal"))
                .click();
    }

    private void log(String message) {
        System.out.println(" ### " + new Date() + " ### " + message);
    }

    private AppiumDriver createAppiumDriver(String udid) {
        log(String.format("Create AppiumDriver with appium server with device udid - '%s'", udid));

        // Appium 1.x
        // DesiredCapabilities capabilities = new DesiredCapabilities();

        // Appium 2.x
        UiAutomator2Options capabilities = new UiAutomator2Options();

        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability("autoGrantPermissions",
                true);
        capabilities.setCapability("app",
                new File("./src/test/resources/sampleApps/AndroidCalculator.apk").getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        URL appiumServerUrl = getAppiumServerUrl();
        AppiumDriver appiumDriver = new AppiumDriver(appiumServerUrl, capabilities);
        log(String.format("Created AppiumDriver for - %s, appiumPort - %s", udid, appiumServerUrl));
        return appiumDriver;
    }
}