@echo off
setlocal

echo Checking JAVA_HOME...
if "%JAVA_HOME%"=="" (
    echo JAVA_HOME is NOT SET AS AN ENVIRONMENT VARIABLE. Set it first then rerun the script
    exit /b 1
)

echo Checking ANDROID_HOME...
if "%ANDROID_HOME%"=="" (
    echo ANDROID_HOME is NOT SET AS AN ENVIRONMENT VARIABLE. Set it first then rerun the script
    exit /b 1
)

echo Setting up Android SDK command line tools...
if not exist "%ANDROID_HOME%\cmdline-tools" (
    mkdir "%ANDROID_HOME%\cmdline-tools"
    echo Downloading android sdk command line tools
    curl -L "https://dl.google.com/android/repository/commandlinetools-win-7583922_latest.zip" -o "%TEMP%\cmdline-tools.zip"
    powershell -Command "Expand-Archive '%TEMP%\cmdline-tools.zip' '%TEMP%'"
    xcopy "%TEMP%\cmdline-tools" "%ANDROID_HOME%\cmdline-tools\" /E /H /C /I /Y
    del "%TEMP%\cmdline-tools.zip"
    rmdir /S /Q "%TEMP%\cmdline-tools"
) else (
    echo ANDROID_HOME command-line tools already present.
)

echo Installing Android SDK packages...
call "%ANDROID_HOME%\cmdline-tools\bin\sdkmanager.bat" "tools" "platform-tools" "platforms;android-31" "build-tools;31.0.0"

echo Installing Node modules...
npm install -g --save opencv4nodejs
npm install -g mjpeg-consumer
npm install -g appium
npm install -g appium-doctor

echo Running appium-doctor...
appium-doctor

echo.
echo PLEASE ENSURE
echo -- ANDROID_HOME is set to %ANDROID_HOME%
echo -- Update PATH:
echo ---- "%%PATH%%;%%ANDROID_HOME%%\platform-tools;%%ANDROID_HOME%%\cmdline-tools\bin"
pause
