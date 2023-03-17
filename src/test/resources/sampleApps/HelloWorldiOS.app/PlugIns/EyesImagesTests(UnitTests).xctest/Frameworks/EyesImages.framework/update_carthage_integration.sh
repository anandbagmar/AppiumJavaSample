# Encrypted variables:
#   - BINTRAY_API_KEY


# 1
# Set bash script to exit immediately if any commands fail.
set -e

# 2
# Get access to set_target_framework_name.sh file, because FRAMEWORK_NAME variable is calculated there.
source "${TRAVIS_BUILD_DIR}/ApplitoolsEyes/Scripts/set_target_framework_name.sh"

FRAMEWORK_NAME="${TARGET_FRAMEWORK_NAME}"

# 3
# Get SDK's version
INFO="${TRAVIS_BUILD_DIR}/ApplitoolsEyes/${FRAMEWORK_NAME}/Info.plist"
VERSION=$( defaults read "$INFO" CFBundleShortVersionString )
echo "SDK version = ${VERSION}"

# 4
# Go to 'Cocoapods Spec Generator' folder
cd "ApplitoolsEyes/Carthage Dependencies JSON Generator"
echo "'Carthage Dependencies JSON Generator' folder"
ls

# 5
# Generate .json with dependencies for new version that is needed in integration with EyesXCUI of EyesImages SDK via Carthage.
# Thor tool is used in generating .podspec file as well during updating Cocoa Pods part. Installation thor .gem is done there.
thor dependencies_json "${FRAMEWORK_NAME}" --version="${VERSION}"
ls

# 6
# Set up file variable
FILE="${FRAMEWORK_NAME}-${VERSION}.json"

# 7
# Set up BINTRAY_USER variable
BINTRAY_USER="antonchuev"

# 8
# Upload new version to Bintray.
curl -T "${FILE}" -"u${BINTRAY_USER}:${BINTRAY_API_KEY}" "https://api.bintray.com/content/applitools/iOS/Carthage${FRAMEWORK_NAME}/${VERSION}/Carthage${FRAMEWORK_NAME}/${VERSION}/${FILE}"

# 9
# Publish new version on Bintray.
curl -X POST -"u${BINTRAY_USER}:${BINTRAY_API_KEY}" "https://api.bintray.com/content/applitools/iOS/Carthage${FRAMEWORK_NAME}/${VERSION}/publish"

# 10
# Return back to build folder
cd "${TRAVIS_BUILD_DIR}"
