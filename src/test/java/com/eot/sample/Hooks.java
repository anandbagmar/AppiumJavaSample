package com.eot.sample;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.URL;

public class Hooks {
    private static AppiumDriverLocalService localAppiumServer;

    @BeforeSuite
    public void beforeAll() {
        System.out.println(String.format("Start local Appium server"));
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        // Use any port, in case the default 4723 is already taken (maybe by another Appium server)
        serviceBuilder.usingAnyFreePort();
        localAppiumServer = AppiumDriverLocalService.buildService(serviceBuilder);
        localAppiumServer.start();
        System.out.println(String.format("Appium server started on url: '%s'",
                                         localAppiumServer.getUrl()
                                                          .toString()));
    }

    @AfterSuite
    public void afterSuite() {
        if (null != localAppiumServer) {
            System.out.println(String.format("Stopping the local Appium server running on: '%s'",
                                             localAppiumServer.getUrl()
                                                              .toString()));
            localAppiumServer.stop();
            System.out.println("Is Appium server running? " + localAppiumServer.isRunning());
        }
    }

    public URL getAppiumServerUrl() {
        return localAppiumServer.getUrl();
    }
}
