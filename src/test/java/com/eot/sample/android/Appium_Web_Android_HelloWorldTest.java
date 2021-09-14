package com.eot.sample.android;

import com.eot.utils.*;
import io.appium.java_client.*;
import io.appium.java_client.remote.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.*;
import org.testng.annotations.*;

import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Appium_Web_Android_HelloWorldTest {

    @Test()
    public void appiumWebTest() throws InterruptedException {
        System.out.println("Start time: " + new Date().toString());
        DriverUtils.setChromeDriverForConnectedDevice();
        AppiumDriver driver = setupMobileWeb();
        driver.get("https://applitools.com/helloworld");
        for (int stepNumber = 0; stepNumber < 5; stepNumber++) {
            driver.findElement(By.linkText("?diff1")).click();
            Thread.sleep(1000);
        }

        driver.findElement(By.tagName("button")).click();
        driver.quit();

        System.out.println("End time: " + new Date().toString());
    }

    private AppiumDriver setupMobileWeb() {
        DesiredCapabilities dc = new DesiredCapabilities(new ChromeOptions());
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12");
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        dc.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
        dc.setCapability("chromedriverExecutable", System.getProperty("webdriver.chrome.driver"));

        // Open browser.
        AppiumDriver driver = null;
        try {
            driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), dc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        return driver;
    }
}