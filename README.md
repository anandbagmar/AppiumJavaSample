# AppiumJavaSample
Sample project for running tests using Java/TestNG/Appium 

## Setting up your machine
* Execute the following scripts to setup your Mac [setup_mac.sh](setup_mac.sh) or Ubuntu [setup_linux.sh](setup_linux.sh) machine automatically 

The mentioned setup scripts install all dependencies required for implementing / running tests on Android devices. To do the setup for iOS devices, run `appium-doctor` and see the list of dependencies that are missing, and install the same.

## Running the tests
### Prerequisites:
* Start appium server manually (and update the url/port if not using the default)
* Have devices connected / emulators started. Accordingly, update the (.*Test.java) test file with the relevant information about the devices

### Tests
This project includes the following tests implemented for Android devices:
* [CalcTest.java](src/test/java/com/eot/sample/CalcTest.java) - run an Appium test against the Calculator app
* [ParallelCalcTest.java](src/test/java/com/eot/sample/ParallelCalcTest.java) - run 2 Appium tests, in parallel, using testng
 
