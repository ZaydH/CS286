#!/bin/bash

export JAR_NAME=kmeans.jar

java -cp $JAR_NAME cs286.Kmeans $1 $2 $3 $4 $5 $6 $7
exit $?
