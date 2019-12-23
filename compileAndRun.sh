#!/usr/bin/env bash
mvn clean compile &&
mvn clean install &&
mvn clean package &&
mvn exec:java -Dexec.mainClass="com.revolut.rest.RestApplication"