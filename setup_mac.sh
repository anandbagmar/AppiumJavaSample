#!/usr/bin/env bash
set -e

arch_name="$(uname -m)"

if [ "${arch_name}" = "x86_64" ]; then
    if [ "$(sysctl -in sysctl.proc_translated)" = "1" ]; then
        echo "Running on Rosetta 2"
    else
        echo "Running on native Intel"
    fi
elif [ "${arch_name}" = "arm64" ]; then
    echo "Running on ARM"
else
    echo "Unknown architecture: ${arch_name}"
fi

function brewCaskInstall () {
echo "Installing $1"
echo "On platform: $arch_name"
    if "brew ls --versions $1" > /dev/null; then
            echo "- $1 already installed"
    else
        if [ "${arch_name}" = "arm64" ]; then
            "arch -x86_64 brew install --cask $1"
        else
            "brew install --cask $1"
        fi
    fi
}

function brewInstall () {
echo "Installing $1"
echo "On platform: $arch_name"
    if brew ls --versions "$1" > /dev/null; then
            echo "- $1 already installed"
    else
        if [ "${arch_name}" = "arm64" ]; then
            arch -x86_64 brew install "$1"
        else
            brew install "$1"
        fi
    fi
}

CURRENT_DIR=$("pwd")
echo "============================================================"
echo "Prerequisites: Install JDK and set JAVA_HOME environment variable. Also set ANDROID_HOME environment variable pointing to a directory where Android SDK should be installed"
echo "============================================================"

echo "CURRENT_DIR - $CURRENT_DIR"
echo "JAVA_HOME - $JAVA_HOME"
echo "ANDROID_HOME - $ANDROID_HOME"
echo "On platform: $arch_name"
echo "============================================================"

[ -z "$JAVA_HOME" ] && echo "JAVA_HOME is NOT SET AS AN ENVIRONMENT VARIABLE. Set it first then rerun the script" && exit 1;
[ -z "$ANDROID_HOME" ] && echo "ANDROID_HOME is NOT SET AS AN ENVIRONMENT VARIABLE. Set it first then rerun the script" && exit 1;

 brewInstall node
 brewInstall coreutils
 brewInstall wget
 brewInstall cmake
 brewInstall carthage
 brewInstall ruby
 brewInstall ffmpeg
 brewInstall mp4box
 brewInstall ideviceinstaller
 brewInstall libimobiledevice
 brewInstall lyft/formulae/set-simulator-location
 brew tap wix/brew
 brewInstall applesimutils
 brewInstall gstreamer
 brewInstall gst-plugins-base
 brewInstall gst-plugins-good
 brewInstall gst-plugins-bad
 brewInstall gst-plugins-ugly
 brewInstall gst-libav

if ! [ -d "$ANDROID_HOME" ] ; then
    mkdir -pv ./temp
    DOWNLOADED_ZIP="./temp/tools.zip"
    echo "ANDROID_HOME ($ANDROID_HOME) directory NOT present. Setting it up now"
    sudo mkdir -pv "$ANDROID_HOME"
    sudo chmod 777 "$ANDROID_HOME"
    echo "Downloading android sdk"
    rm -f "$DOWNLOADED_ZIP" 2> /dev/null
    wget https://dl.google.com/android/repository/commandlinetools-mac-8092744_latest.zip -O "$DOWNLOADED_ZIP"
    unzip $DOWNLOADED_ZIP -d "$ANDROID_HOME"
    sleep 5
else
    echo "ANDROID_HOME ($ANDROID_HOME) directory present. already set. IF YOU WANT TO REINSTALL, delete directory - $ANDROID_HOME and run the ./setup_mac.sh script again"
fi

 echo "Setup android sdk"
 cd "$ANDROID_HOME"/cmdline-tools/bin
 pwd

 echo "Installing ./sdkmanager --sdk_root=$ANDROID_HOME tools cmdline-tools;latest platform-tools platforms;android-32 platforms;android-32 build-tools;32.0.0"
 pwd
 ./sdkmanager --sdk_root="$ANDROID_HOME" "tools" "cmdline-tools;latest" "platform-tools" "platforms;android-32" "platforms;android-32" "build-tools;32.0.0"

 sleep 5
 echo "Done installing Android SDK in $ANDROID_HOME"

 if ! [ -d "$ANDROID_HOME"/bundle-tool ] ; then
     echo "Setup bundletool"
     cd "$ANDROID_HOME"
     mkdir -pv bundle-tool
     cd bundle-tool
     wget https://github.com/google/bundletool/releases/download/1.8.0/bundletool-all-1.8.0.jar -O bundletool.jar
     chmod +x bundletool.jar
 else
     echo "$ANDROID_HOME/bundle-tool already setup"
 fi

cd "$CURRENT_DIR"
pwd
# echo "Install flick ruby gem"
# sudo gem install flick
# echo "Install opencv4nodejs"
# npm install -g opencv4nodejs
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
echo "---- 'export PATH=\$PATH:\$ANDROID_HOME/platform-tools:\$ANDROID_HOME/tools:\$ANDROID_HOME/tools/bin:\$ANDROID_HOME/bundle-tool'"
pwd