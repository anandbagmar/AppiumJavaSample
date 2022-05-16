package com.eot.sample.android;

import com.eot.sample.Hooks;
import com.eot.utils.DriverUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Appium_Web_Android_HelloWorldTest
        extends Hooks {
    private AppiumDriver driver;

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
        DesiredCapabilities dc = new DesiredCapabilities(new ChromeOptions());
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME,
                         "Android");
        dc.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                         "11");
        dc.setCapability(MobileCapabilityType.DEVICE_NAME,
                         "Android");
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                         "UiAutomator2");
        dc.setCapability(MobileCapabilityType.BROWSER_NAME,
                         "Chrome");
        dc.setCapability("chromedriverExecutable",
                         System.getProperty("webdriver.chrome.driver"));

        // Open browser.
        AppiumDriver driver = null;
        driver = new AppiumDriver(getAppiumServerUrl(),
                                  dc);
        driver.manage()
              .timeouts()
              .implicitlyWait(60,
                              TimeUnit.SECONDS);
        return driver;
    }
}