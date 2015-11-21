#!/bin/bash

export JAR_NAME=kmeans.jar

java -jar $JAR_NAME $1 $2 $3 $4 $5 $6 $7
exit $?
