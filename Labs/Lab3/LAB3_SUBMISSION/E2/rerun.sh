#!/bin/bash

if [ "$#" -ne 5 ]; then
	printf "Wrong number of input arguments. Exiting.\n"
	exit 1
fi

java -cp knn.jar cs286.Knn $1 $2 $3 $4 $5
exit #?
