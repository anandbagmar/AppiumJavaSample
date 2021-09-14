# AppiumJavaSample
Sample project for running tests using Java/TestNG/Appium 

## Setting up your machine
### Prerequisites before running the script for Appium setup 
* Set `JAVA_HOME` as an environment variable

* Set `ANDROID_HOME` as an environment variable - pointing to the directory where Android SDK should be setup

* Execute the following scripts to setup your Mac [setup_mac.sh](setup_mac.sh) or Ubuntu [setup_linux.sh](setup_linux.sh) machine automatically 
> The above script will install all dependencies required for implementing / running tests on Android devices. To do the setup for iOS devices, run `appium-doctor` and see the list of dependencies that are missing, and install the same.

> You may be prompted for password or confirmations along the way 

## Running the tests
### Prerequisites:
* Start appium server manually (and update the url/port if not using the default)
* Have devices connected / emulators started. Accordingly, update the (.*Test.java) test file with the relevant information about the devices
* The device should have the Calculator app

### Tests
This project includes the following tests implemented for Android & iOS devices:

#### Android
* [Appium_Native_Android_MessagesTest.java](src/test/java/com/eot/sample/android/Appium_Native_Android_MessagesTest.java) - run an Appium test against the Messages app
* [Appium_Native_Android_ParallelCalcTest.java](src/test/java/com/eot/sample/android/Appium_Native_Android_ParallelCalcTest.java) - run 2 Appium tests, in parallel, using testng
* [Appium_Web_Android_HelloWorldTest.java](src/test/java/com/eot/sample/android/Appium_Web_Android_HelloWorldTest.java) - runs an appium test against a Chrome browser (mobile-web) in the connected device

#### iOS
* [Appium_Native_iOS_HelloWorldTest.java](src/test/java/com/eot/sample/ios/Appium_Native_iOS_HelloWorldTest.java) - run an Appium test against the Messages app
* [Appium_Web_iOS_HelloWorldTest.java](src/test/java/com/eot/sample/ios/Appium_Web_iOS_HelloWorldTest.java) - run 2 Appium tests, in parallel, using testng

