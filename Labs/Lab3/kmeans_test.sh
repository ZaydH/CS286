#!/bin/bash

# Specify an input text file.
export INPUT_FILE=$1
export OUT_DIR=test_results

# Create a new test results folder
rm -rf $OUT_DIR &> /dev/null
mkdir $OUT_DIR

# Do an extra input parameter check
export k=1
export maxIterations=1
export delta=1
export dist=cosine
export inputFile=$INPUT_FILE
export outputFile=dummy
echo "Perform an extra parameter check."
java kmeans.jar cs286.Kmeans $k $maxIterations $delta $dist $inputFile $outputFile extra
if [$? -eq 0]; then
	echo "ERROR: No invalid parameter count check."
fi
