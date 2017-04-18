#!/usr/bin/env bash

# install github-release tool
curl -L -o ~/github-release.tar.bz2 https://github.com/aktau/github-release/releases/download/v0.7.2/linux-amd64-github-release.tar.bz2
tar -C ~ -jxf ~/github-release.tar.bz2
export PATH=~/bin/linux/amd64:"$PATH"

ARTIFACT="$(echo $CIRCLE_ARTIFACTS/build-artifacts/backend-*.war)"
TAG_VERSION=$CIRCLE_TAG

test -f "$ARTIFACT"

github-release release \
  --user matthiasbalke \
  --repo whoknows \
  --tag "$TAG_VERSION" \
  --name "$TAG_VERSION" \
  --description "Released on $(date +%Y-%m-%d)" \
  --draft

github-release upload \
  --user matthiasbalke \
  --repo whoknows \
  --tag "$TAG_VERSION" \
  --name "$(basename "$ARTIFACT")" \
  --file "$ARTIFACT"
