#!/bin/bash
#set -x

# install github-release tool
#curl -L -o ~/github-release.tar.bz2 https://github.com/aktau/github-release/releases/download/v0.7.2/linux-amd64-github-release.tar.bz2
#tar -C ~ -jxf ~/github-release.tar.bz2
#export PATH=~/bin/linux/amd64:"$PATH"

#curl -L https://bintray.com/reflectoring/releases/download_file?file_path=harvester-0.2.0.493.jar

export GITHUB_TOKEN="<token here>"

USERNAME=matthiasbalke
REPOSITORY=whoknows
BUILD_NUMBER=10
TAG_VERSION=0.1.0

CIRCLE_BUILD_URL=https://circleci.com/api/v1.1/project/github/$USERNAME/$REPOSITORY/$BUILD_NUMBER
CIRCLE_ARTIFACTS_URL=$CIRCLE_BUILD_URL/artifacts

# get VCS revision
echo "fetching git hash for build #$BUILD_NUMBER"
JSON=$(curl -s $CIRCLE_BUILD_URL)
COMMIT_HASH=$(echo $JSON | jq -r '.vcs_revision')
echo "> hash: $COMMIT_HASH"
echo ""

echo "fetching artifacts for build #$BUILD_NUMBER"
JSON_ARTIFACTS=$(curl -s $CIRCLE_ARTIFACTS_URL)

# create download folder
DOWNLOAD_DIR=`pwd`/build/download
rm -rf $DOWNLOAD_DIR
mkdir -p $DOWNLOAD_DIR
cd $DOWNLOAD_DIR

# download matching artifacts
echo "downloading artifacts ..."
# TODO extract download file function
echo $JSON_ARTIFACTS | jq '.[].url' | while read artifact; do
    if [[ $artifact == *"war"* ]]
    then
        ARTIFACT=$(echo $artifact | cut -d '"' -f 2)
        echo "> $ARTIFACT"
        curl -s -L -O $(echo $ARTIFACT)
    fi
done
echo ""

echo "creating git tag '$TAG_VERSION' ..."
# create git tag
git tag -a $TAG_VERSION $COMMIT_HASH -m "released version $TAG_VERSION"
echo ""

echo "pushing git tag $TAG_VERSION ..."
git push origin $TAG_VERSION
echo ""

echo "creating github release $TAG_VERSION ..."
# create github release
github-release release \
  --user $USERNAME \
  --repo $REPOSITORY \
  --tag "$TAG_VERSION" \
  --name "$TAG_VERSION" \
  --description "Released on $(date +%Y-%m-%d)" \
  --draft
echo ""

echo "uploading github release artifacts ..."
for artifact in $DOWNLOAD_DIR/*.*; do
    echo "> $(basename $artifact)"

    # upload artifacts
    github-release upload \
      --user $USERNAME \
      --repo $REPOSITORY \
      --tag "$TAG_VERSION" \
      --name "$(basename "$artifact")" \
      --file "$artifact"
done
echo ""

echo "publishing github release ..."
github-release edit \
  --user $USERNAME \
  --repo $REPOSITORY \
  --tag "$TAG_VERSION" \
  --name "$TAG_VERSION"
echo ""
echo "successfully created release $TAG_VERSION !"
