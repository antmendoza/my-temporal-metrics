#!/bin/sh

cd java-sdk-metrics

mvn clean install

docker build . -t my-metric-worker