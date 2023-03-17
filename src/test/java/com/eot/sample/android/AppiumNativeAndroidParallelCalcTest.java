package com.eot.sample.android;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

import static com.eot.sample.Hooks.*;

public class AppiumNativeAndroidParallelCalcTest {

    private final HashMap<String, AppiumDriver> drivers = new HashMap<>();

    @DataProvider(name = "device-provider", parallel = true)
    public Object[][] provide() {
        return new Object[][]{{"emulator-5554", 2, 5}, {"emulator-5556", 3, 6}};
    }

    @BeforeSuite
    public void beforeAll() {
        startAppiumServer();
    }

    @AfterSuite
    public void afterAll() {
        stopAppiumServer();
    }

    @BeforeMethod
    public void beforeMethod(Object[] testArgs) {
        String methodName = ((Method) testArgs[0]).getName();
        String udid = (String) testArgs[2];
        log(String.format("Running test '%s' on '%s'", methodName, udid));

        log(String.format("Create AppiumDriver for - %s:%s", udid));
        AppiumDriver driver = createAppiumDriver(getAppiumServerUrl(), udid);
        drivers.put(udid, driver);
        log(String.format("Created AppiumDriver for - %s:%s", udid));
    }

    @AfterMethod
    public void afterMethod(Object[] testArgs) {
        log(testArgs.toString());
        String methodName = ((Method) testArgs[0]).getName();
        ITestResult result = ((ITestResult) testArgs[1]);
        log(String.format("Test '%s' result: '%s'", methodName, result.toString()));
        String udid = (String) testArgs[2];
        Integer systemPort = (Integer) testArgs[3];

        AppiumDriver driver = drivers.get(udid);

        try {
            if (null != driver) {
                driver.quit();
            }

            log(String.format("Visual Validation Results for - %s:%s", udid, systemPort));
        } catch (Exception e) {
            log("Exception - " + e.getMessage());
            e.printStackTrace();
        } finally {
        }
    }

    @Test(dataProvider = "device-provider", threadPoolSize = 2)
    public void runTest(Method method, ITestResult testResult, String udid, int num1, int num2) {
        log(String.format("Runnng test on %s:%s, appiumPort - ", udid));
        log(String.format("drivers.size()=%d", drivers.size()));
        AppiumDriver driver = drivers.get(udid);
        try {
            driver.findElement(By.id("digit_" + num1))
                    .click();
            driver.findElement(By.id("op_add"))
                    .click();
            driver.findElement(By.id("digit_" + num2))
                    .click();
        } catch (Exception e) {
            log(e.toString());
        } finally {
            if (null != driver) {
                driver.quit();
            }
        }
    }

    private void log(String message) {
        System.out.println(" ### " + new Date() + " ### " + message);
    }

    private AppiumDriver createAppiumDriver(URL appiumServerUrl, String udid) {
        // Appium 1.x
        // DesiredCapabilities capabilities = new DesiredCapabilities();

        // Appium 2.x
        UiAutomator2Options capabilities = new UiAutomator2Options();

        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability("app", "src/test/resources/sampleApps/AndroidCalculator.apk");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        return new AppiumDriver(appiumServerUrl, capabilities);
    }
}