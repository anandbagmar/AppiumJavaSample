#!/usr/bin/env bash
set -e

function brewCaskInstall () {
echo "Installing $1"
    if brew ls --versions $1 > /dev/null; then
            echo "- $1 already installed"
    else
            brew cask install $1
    fi
}

function brewInstall () {
echo "Installing $1"
    if brew ls --versions $1 > /dev/null; then
            echo "- $1 already installed"
    else
            brew install $1
    fi
}

CURRENT_DIR=`pwd`
echo "CURRENT_DIR - " $CURRENT_DIR
[ -z "$JAVA_HOME" ] && echo "JAVA_HOME is NOT SET AS AN ENVIRONMENT VARIABLE. Set it first then rerun the script" && exit 1;
[ -z "$ANDROID_HOME" ] && echo "ANDROID_HOME is NOT SET AS AN ENVIRONMENT VARIABLE. Set it first then rerun the script" && exit 1;
echo "JAVA_HOME - " $JAVA_HOME
echo "ANDROID_HOME - " $ANDROID_HOME

brew tap caskroom/versions
brewCaskInstall java8
brewInstall node
brewInstall coreutils
brewInstall wget
brewInstall cmake
brewInstall carthage
brewInstall ruby
brewInstall ffmpeg
brewInstall mp4box
brewInstall ideviceinstaller

if ! [ -d "$ANDROID_HOME" ] ; then
    mkdir -pv ./temp
    DOWNLOADED_ZIP="./temp/tools.zip"
    echo "ANDROID_HOME ($ANDROID_HOME) directory NOT present. Setting it up now"
    sudo mkdir -pv $ANDROID_HOME
    sudo chmod 777 $ANDROID_HOME
    echo "Downloading android sdk"
    rm -f $DOWNLOADED_ZIP 2> /dev/null
    wget https://dl.google.com/android/repository/commandlinetools-mac-6858069_latest.zip -O $DOWNLOADED_ZIP
    unzip $DOWNLOADED_ZIP -d $ANDROID_HOME
    sleep 5
else
    echo "ANDROID_HOME ($ANDROID_HOME) directory present. already set. IF YOU WANT TO REINSTALL, delete directory - $ANDROID_HOME and run the ./setup_mac.sh script again"
fi

echo "Setup android sdk"
cd $ANDROID_HOME/tools/bin
pwd

echo "Installing ./sdkmanager tools platform-tools platforms;android-28 build-tools;28.0.3"
pwd
./sdkmanager "tools" "platform-tools" "platforms;android-28" "build-tools;28.0.3"

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
echo "Install flick ruby gem"
sudo gem install flick
echo "Install opencv4nodejs"
npm install -g opencv4nodejs
echo "Installed node version"
node -v
echo "Install ios-deploy"
npm install -g ios-deploy
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
echo "---- 'export PATH=\$PATH:\$ANDROID_HOME/platform-tools:\$ANDROID_HOME/tools:\$ANDROID_HOME/bundle-tool'"
pwd