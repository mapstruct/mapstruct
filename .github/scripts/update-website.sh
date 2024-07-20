#!/bin/bash
#
# Copyright MapStruct Authors.
#
# Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
#

# env vars:
# VERSION
# GH_BOT_EMAIL

# This script has been inspired by the JReleaser update-website.sh (https://github.com/jreleaser/jreleaser/blob/main/.github/scripts/update-website.sh)
set -e

function computePlainVersion() {
  echo $1 | sed 's/\([[:digit:]]*\)\.\([[:digit:]]*\)\.\([[:digit:]]*\).*/\1.\2.\3/'
}

function computeMajorMinorVersion() {
  echo $1 | sed 's/\([[:digit:]]*\)\.\([[:digit:]]*\).*/\1.\2/'
}

function isStable() {
  local PLAIN_VERSION=$(computePlainVersion $1)
  if [ "${PLAIN_VERSION}" == "$1" ]; then
      echo "yes"
  else
      echo "no"
  fi
}

STABLE=$(isStable $VERSION)
MAJOR_MINOR_VERSION=$(computeMajorMinorVersion $VERSION)

DEV_VERSION=`grep devVersion config.toml |  sed 's/.*"\(.*\)"/\1/'`
MAJOR_MINOR_DEV_VERSION=$(computeMajorMinorVersion $DEV_VERSION)
STABLE_VERSION=`grep stableVersion config.toml |  sed 's/.*"\(.*\)"/\1/'`
MAJOR_MINOR_STABLE_VERSION=$(computeMajorMinorVersion $STABLE_VERSION)

echo "📝 Updating versions"

SEDOPTION="-i"
if [[ "$OSTYPE" == "darwin"* ]]; then
  SEDOPTION="-i ''"
fi

sed $SEDOPTION -e "s/^devVersion = \"\(.*\)\"/devVersion = \"${VERSION}\"/g" config.toml

if [ "${STABLE}" == "yes" ]; then
  sed $SEDOPTION -e "s/^stableVersion = \"\(.*\)\"/stableVersion = \"${VERSION}\"/g" config.toml
  if [ "${MAJOR_MINOR_STABLE_VERSION}" != ${MAJOR_MINOR_VERSION} ]; then
    echo "📝 Updating new stable version"
    # This means that we have a new stable version and we need to change the order of the releases.
    sed $SEDOPTION -e "s/^order = \(.*\)/order = 500/g" data/releases/${MAJOR_MINOR_VERSION}.toml
    NEXT_STABLE_ORDER=$((`ls -1 data/releases | wc -l` - 2))
    sed $SEDOPTION -e "s/^order = \(.*\)/order = ${NEXT_STABLE_ORDER}/g" data/releases/${MAJOR_MINOR_STABLE_VERSION}.toml
    git add data/releases/${MAJOR_MINOR_STABLE_VERSION}.toml
  fi
elif [ "${MAJOR_MINOR_DEV_VERSION}" != "${MAJOR_MINOR_VERSION}" ]; then
  echo "📝 Updating new dev version"
  # This means that we are updating for a new dev version, but the last dev version is not the one that we are doing.
  # Therefore, we need to update add the new data configuration
  cp data/releases/${MAJOR_MINOR_DEV_VERSION}.toml data/releases/${MAJOR_MINOR_VERSION}.toml
  sed $SEDOPTION -e "s/^order = \(.*\)/order = 1000/g" data/releases/${MAJOR_MINOR_VERSION}.toml
fi

sed $SEDOPTION -e "s/^name = \"\(.*\)\"/name = \"${VERSION}\"/g" data/releases/${MAJOR_MINOR_VERSION}.toml
sed $SEDOPTION -e "s/^releaseDate = \(.*\)/releaseDate = $(date +%F)/g" data/releases/${MAJOR_MINOR_VERSION}.toml
git add data/releases/${MAJOR_MINOR_VERSION}.toml
git add config.toml

echo "📝 Updating distribution resources"
tar -xf tmp/mapstruct-${VERSION}-dist.tar.gz --directory tmp
rm -rf static/documentation/${MAJOR_MINOR_VERSION}
cp -R tmp/mapstruct-${VERSION}/docs static/documentation/${MAJOR_MINOR_VERSION}
mv static/documentation/${MAJOR_MINOR_VERSION}/reference/html/mapstruct-reference-guide.html static/documentation/${MAJOR_MINOR_VERSION}/reference/html/index.html
git add static/documentation/${MAJOR_MINOR_VERSION}

git config --global user.email "${GH_BOT_EMAIL}"
git config --global user.name "GitHub Action"
git commit -a -m "Releasing version ${VERSION}"
git push
