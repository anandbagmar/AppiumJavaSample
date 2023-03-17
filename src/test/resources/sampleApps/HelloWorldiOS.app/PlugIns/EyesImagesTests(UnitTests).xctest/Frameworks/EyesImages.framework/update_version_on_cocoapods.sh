# Custom environment variables:
#   - GH_TOKEN
#   - FRAMEWORK_NAME
#   - BINTRAY_USER
#   - BINTRAY_API_KEY
# Default environment variables:
#   - TRAVIS_BUILD_DIR
#
# Change values of custom environment variables at https://travis-ci.com/applitools/eyes.ios/settings
# List of default environment variables at: https://docs.travis-ci.com/user/environment-variables/

# 1
# Set bash script to exit immediately if any commands fail.
set -e

# 2
# Setup some constants for use later on.
COCOAPODS_RELEASE_FOLDER="${TRAVIS_BUILD_DIR}/ApplitoolsEyes/${FRAMEWORK_NAME}/Release/CocoaPods/${FRAMEWORK_NAME}"

# 3
# Copy the device version of framework to the CocoaPods release folder.
echo "--- Copying device version of framework to CocoPods release folder..."
cp -r "${TRAVIS_BUILD_DIR}/ApplitoolsEyes/build/Release-iphoneos/" "${COCOAPODS_RELEASE_FOLDER}"

# 4
# Replace the framework exequtable within the framework with a new version created by merging the device and simulator frameworks' executables with lipo.
echo "--- Merging the device and simulator frameworks' executables with lipo..."
lipo -create -output "${COCOAPODS_RELEASE_FOLDER}/${FRAMEWORK_NAME}.framework/${FRAMEWORK_NAME}" "${TRAVIS_BUILD_DIR}/ApplitoolsEyes/build/Release-iphoneos/${FRAMEWORK_NAME}.framework/${FRAMEWORK_NAME}" "${TRAVIS_BUILD_DIR}/ApplitoolsEyes/build/Release-iphonesimulator/${FRAMEWORK_NAME}.framework/${FRAMEWORK_NAME}"

# 5
# Remove .dsym file from CocoaPods release folder.
echo "--- Removing .dsym file from CocoaPods release folder..."
rm -rf "${COCOAPODS_RELEASE_FOLDER}/${FRAMEWORK_NAME}.framework.dSYM"

# 6
# Copy LICENSE to CocoaPods release folder.
echo "--- Coping LICENSE to CocoaPods release folder..."
cp -r LICENSE "${COCOAPODS_RELEASE_FOLDER}"

# 7
# Copy Release folder to project directory excluding hidden files.
echo "--- Coping release folder to project directory excluding hidden files..."
rsync -av --exclude=".*" "${COCOAPODS_RELEASE_FOLDER}" .

# 8
# Get SDK's version.
INFO="${TRAVIS_BUILD_DIR}/ApplitoolsEyes/${FRAMEWORK_NAME}/Info.plist"
VERSION=$( defaults read "$INFO" CFBundleShortVersionString )
echo "Getting the SDK version = ${VERSION}"

# 9
# Compress CocoaPods release folder
echo "--- Compressing CocoaPods release folder..."
zip -r "${FRAMEWORK_NAME}-${VERSION}.zip" "${FRAMEWORK_NAME}"

# 10
# Create variables for uploading to Bintray. BINTRAY_API_KEY variable is encrypted and locates in .yml.
FILE="${FRAMEWORK_NAME}-${VERSION}.zip"
BINTRAY_USER="antonchuev"

# 11
# Upload new version to Bintray.
echo "--- Uploading new version to Bintray..."
curl -T "${FILE}" -"u${BINTRAY_USER}:${BINTRAY_API_KEY}" "https://api.bintray.com/content/applitools/iOS/${FRAMEWORK_NAME}/${VERSION}/${FRAMEWORK_NAME}/${VERSION}/${FILE}"

# 12
# Publish new version to Bintray.
echo "--- Publishing new version to Bintray..."
curl -X POST -"u${BINTRAY_USER}:${BINTRAY_API_KEY}" "https://api.bintray.com/content/applitools/iOS/${FRAMEWORK_NAME}/${VERSION}/publish"

# 13
# Go to 'Cocoapods Spec Generator' folder
cd "ApplitoolsEyes/Cocoapods Spec Generator"

# 14
# Install bundler gem.
echo "--- Installing bundler gem..."
gem install bundler
bundle install

# 15
# Generate .podspec file
echo "--- Generating .podspec file..."
thor podspec "${FRAMEWORK_NAME}" --version="${VERSION}"

# 16
# Push Podspec to Cocoa pods repo.
echo "--- Pushing .podspec file to Cocoa pods..."
source ~/.rvm/scripts/rvm
rvm use default
pod trunk push "${FRAMEWORK_NAME}.podspec" --allow-warnings

