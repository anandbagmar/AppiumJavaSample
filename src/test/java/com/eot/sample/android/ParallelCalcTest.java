package com.eot.sample.android;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

public class ParallelCalcTest {

    private final static String APPIUM_SERVER_URL = "http://localhost:port/wd/hub";
    private HashMap<String, AppiumDriver<MobileElement>> drivers = new HashMap<>();

    @DataProvider(name = "device-provider", parallel = true)
    public Object[][] provide() {
        return new Object[][]{
                {"4723", "emulator-5554", 8201, 2, 5},
                {"4723", "emulator-5556", 8202, 3, 6}
        };
    }

    @BeforeSuite
    public void setUp() {
    }

    @BeforeMethod
    public void beforeMethod(Object[] testArgs) {
        String methodName = ((Method) testArgs[0]).getName();
        ITestResult result = ((ITestResult) testArgs[1]);
        String appiumPort = testArgs[2].toString();
        String udid = (String) testArgs[3];
        Integer systemPort = (Integer) testArgs[4];

        log(String.format("Create AppiumDriver for - %s:%s, appiumPort - %s", udid, systemPort, appiumPort));
        AppiumDriver driver = createAppiumDriver(appiumPort, udid, systemPort);
        drivers.put(udid, driver);
        log(String.format("Created AppiumDriver for - %s:%s, appiumPort - %s", udid, systemPort, appiumPort));
    }

    @AfterMethod
    public void afterMethod(Object[] testArgs) {
        log(testArgs.toString());
        String methodName = ((Method) testArgs[0]).getName();
        ITestResult result = ((ITestResult) testArgs[1]);
        String appiumPort = testArgs[2].toString();
        String udid = (String) testArgs[3];
        Integer systemPort = (Integer) testArgs[4];

        AppiumDriver driver = drivers.get(udid);

        try {
            if (null != driver) {
                driver.quit();
            }

            log(String.format("Visual Validation Results for - %s:%s, appiumPort - %s", udid, systemPort, appiumPort));
        } catch (Exception e) {
            log("Exception - " + e.getMessage());
            e.printStackTrace();
        } finally {
        }
    }

    @Test(dataProvider = "device-provider", threadPoolSize = 2)
    public void runTest(Method method, ITestResult testResult, String appiumPort, String udid, Integer systemPort, int num1, int num2) {
        log(String.format("Runnng test on %s:%s, appiumPort - ", udid, systemPort, appiumPort));
        log(String.format("drivers.size()=%d", drivers.size()));
        AppiumDriver driver = drivers.get(udid);
        try {
            driver.findElement(By.id("digit_" + num1)).click();
            driver.findElement(By.id("op_add")).click();
            driver.findElement(By.id("digit_" + num2)).click();
        } catch (Exception e) {
            log(e.toString());
        } finally {
            if (null != driver) {
                driver.quit();
            }
        }
    }

    private void log(String message) {
        System.out.println(" ### " + new Date().toString() + " ### " + message);
    }

    private AppiumDriver createAppiumDriver(String appiumPort, String udid, Integer systemPort) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            capabilities.setCapability(MobileCapabilityType.UDID, udid);
            capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort);
            capabilities.setCapability("appPackage", "com.android.calculator2");
            capabilities.setCapability("appActivity", ".Calculator");
            capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
            return new AppiumDriver<AndroidElement>(new URL(APPIUM_SERVER_URL.replace("port", appiumPort)), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in creating Appium Driver");
        }
    }
}