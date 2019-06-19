# AppiumJavaSample
Sample project for running tests using Java/TestNG/Appium 

This project includes the following:
* scripts to setup your mac [setup_mac.sh](setup_mac.sh) or ubuntu [setup_linux.sh](setup_linux.sh) machine automatically 
* [CalcTest.java](src/test/java/com/eot/sample/CalcTest.java) - run an Appium test against the Calculator app
* [ParallelCalcTest.java](src/test/java/com/eot/sample/ParallelCalcTest.java) - run 2 Appium tests, in parallel, using testng

## Prerequisites:
* Start appium server manually (and update the url/port if not using the default)
* Have devices connected / emulators started. Accordingly, update the (.*Test.java) test file with the relevant information about the devices
