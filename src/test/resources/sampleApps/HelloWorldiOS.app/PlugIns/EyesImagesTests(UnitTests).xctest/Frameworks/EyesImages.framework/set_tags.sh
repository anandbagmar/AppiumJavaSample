#!/bin/sh

#  set_tags.sh
#  Applitools
#
#  Created by Anton Chuev on 4/17/18.
#  Copyright © 2018 Applitools. All rights reserved.

# Custom environment variables:
#   - GH_TOKEN
#   - FRAMEWORK_NAME
# Default environment variables:
#   - TRAVIS_REPO_SLUG
#   - TRAVIS_BUILD_DIR
#
# Change values of custom environment variables at https://travis-ci.com/applitools/eyes.ios/settings
# List of default environment variables at: https://docs.travis-ci.com/user/environment-variables/

BRANCH="master"

# Get SDK's version
INFO="${TRAVIS_BUILD_DIR}/ApplitoolsEyes/${FRAMEWORK_NAME}/Info.plist"
VERSION=$( defaults read "$INFO" CFBundleShortVersionString )
echo "SDK version = ${VERSION}"

# Are we on the right branch?
if [ "$TRAVIS_BRANCH" = "$BRANCH" ]; then

    # Is this not a Pull Request?
    if [ "$TRAVIS_PULL_REQUEST" = false ]; then

        # Is this not a build which was triggered by setting a new tag?
        if [ -z "$TRAVIS_TAG" ]; then

            echo -e "Starting to tag commit.\n"

            git config --global user.email "travis@travis-ci.org"
            git config --global user.name "Travis"

            # Add tag and push to master.
            git tag -a v"${VERSION}-${FRAMEWORK_NAME}" -m "Travis build pushed a tag."
            git push https://$GH_TOKEN@github.com/$TRAVIS_REPO_SLUG.git --tags
            git fetch origin

            echo -e "Done commit with tags.\n"

        fi
    fi
fi

echo "SUCCESS!"
