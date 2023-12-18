#!/bin/sh

cd java-sdk-metrics
./mvnw compile exec:java -Dexec.mainClass="com.antmendoza.temporal.Starter2"
