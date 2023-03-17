# Custom environment variables:
#   - FRAMEWORK_NAME
# Change values of custom environment variables at https://travis-ci.com/applitools/eyes.ios/settings
# List of default environment variables at: https://docs.travis-ci.com/user/environment-variables/

# 1
# Set bash script to exit immediately if any commands fail.
set -e

# 2
# Build the framework for device and for simulator (using all needed architectures).
echo "Creating of builds for device and simulator..."
xcodebuild -project "ApplitoolsEyes/Applitools.xcodeproj" -target "${FRAMEWORK_NAME}" -configuration Release -arch arm64 -arch armv7 -arch armv7s only_active_arch=no defines_module=yes -sdk "iphoneos"
xcodebuild -project "ApplitoolsEyes/Applitools.xcodeproj" -target "${FRAMEWORK_NAME}" -configuration Release -arch x86_64 -arch i386 only_active_arch=no defines_module=yes -sdk "iphonesimulator"
echo "Builds were created!"
