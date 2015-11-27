#!/bin/bash

if [ "$#" -ne 7 ]; then
	printf "Wrong number of input arguments.\n"
	exit 1
fi

export JAR_NAME=kmeans.jar

java -cp $JAR_NAME cs286.Kmeans $1 $2 $3 $4 $5 $6 $7
exit $?
