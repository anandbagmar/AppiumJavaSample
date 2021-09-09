#!/usr/bin/env bash
set -e

function aptGet () {
echo "Installing $1"
    sudo apt-get install $1
}

CURRENT_DIR=`pwd`
echo "CURRENT_DIR - " $CURRENT_DIR
echo "ANDROID_HOME - " $ANDROID_HOME

[ -z "$JAVA_HOME" ] && echo "JAVA_HOME is NOT SET AS AN ENVIRONMENT VARIABLE. Set it first then rerun the script" && exit 1;

aptGet node
aptGet wget
aptGet ruby
aptGet ffmpeg
aptGet cmake
aptGet ideviceinstaller

if ! [ -d "$ANDROID_HOME" ] ; then
    mkdir -pv ./temp
    DOWNLOADED_ZIP="./temp/tools.zip"
    echo "ANDROID_HOME ($ANDROID_HOME) directory NOT present. Setting it up now"
    sudo mkdir -pv $ANDROID_HOME
    sudo chmod 777 $ANDROID_HOME
    echo "Downloading android sdk"
    rm -f $DOWNLOADED_ZIP 2> /dev/null
    wget https://dl.google.com/android/repository/commandlinetools-linux-7583922_latest.zip -O $DOWNLOADED_ZIP
    unzip $DOWNLOADED_ZIP -d $ANDROID_HOME
    sleep 5
else
    echo "ANDROID_HOME ($ANDROID_HOME) directory present. already set. IF YOU WANT TO REINSTALL, delete directory - $ANDROID_HOME and run the ./setup_linux.sh script again"
fi

echo "Setup android sdk"
cd $ANDROID_HOME/tools/bin
pwd

echo "Installing ./sdkmanager tools platform-tools platforms;android-31 build-tools;31.0.0"
pwd
touch ~/.android/repositories.cfg
./sdkmanager "tools" "platform-tools" "platforms;android-31" "build-tools;31.0.0"

sleep 5
echo "Done installing android sdk"

if ! [ -d "$ANDROID_HOME/bundle-tool" ] ; then
    echo "Setup bundletool"
    cd $ANDROID_HOME
    mkdir -pv bundle-tool
    cd bundle-tool
    wget https://github.com/google/bundletool/releases/download/0.9.0/bundletool-all-0.9.0.jar -O bundletool.jar
    chmod +x bundletool.jar
else
    echo "$ANDROID_HOME/bundle-tool already setup"
fi

cd $CURRENT_DIR
pwd
echo "Installed node version"
node -v
echo "Install opencv4nodejs"
npm install -g --save opencv4nodejs
echo "Install mjpeg-consumer"
npm install -g mjpeg-consumer
echo "Install node modules - appium"
npm install -g appium
sleep 5
echo "Install node modules - appium-doctor"
npm install -g appium-doctor
sleep 5
echo "Run appium-doctor"
appium-doctor

echo "PLEASE ENSURE"
echo "-- ANDROID_HOME is set to $ANDROID_HOME"
echo "-- Update PATH:"
echo "---- 'export PATH=\$PATH:\$ANDROID_HOME/platform-tools:\$ANDROID_HOME/tools:\$ANDROID_HOME/tools/bin:\$ANDROID_HOME/bundle-tool'"
pwd
