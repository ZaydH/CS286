#!/bin/bash

# Specify an input text file.
if [ "$#" -eq "0" ]; then
        printf "No input file specified.  Defaulting to \"iris-data.txt\".\n\n"
        export INPUT_FILE="iris-data.txt"
else
        export INPUT_FILE=$1
        printf "Input file set to ${INPUT_FILE}.\n\n"
fi
export OUT_DIR=test_output
export COMPARE_DIR=verification_results

# Rebuild the JAR just to be extra dilligent
printf "Building the KNN JAR file.\n"
bash rebuild.sh &> /dev/null
printf "KNN JAR file built.\n\n\n"

# Create a new test results folder
rm -rf $OUT_DIR &> /dev/null
mkdir $OUT_DIR

# Initialize the counter
COUNTER=1

# Test - Do an extra input parameter check
printf "Test #${COUNTER} - Too many input parameters check.\n"
export holdOut=0.1
export k=3
export dist=cosine
export inputFile=$INPUT_FILE
export outputFile=dummy
bash rerun.sh $holdOut $k $dist $inputFile $outputFile extra
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1


# Test - Perform an insufficient parameter count check
printf "Test #${COUNTER} - Insufficient input parameter check.\n"
export holdOut=0.1
export k=3
export dist=cosine
export inputFile=$INPUT_FILE
export outputFile=dummy
bash rerun.sh $holdOut $k $dist $inputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1




# Test - holdOut Value check
export holdOut=0.049
export k=3
export dist=cosine
export inputFile=$INPUT_FILE
export outputFile=dummy
printf "Test #${COUNTER} - <hold-out %> = ${holdOut}.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1




# Test - holdOut Value check
export holdOut=0.21
export k=3
export dist=cosine
export inputFile=$INPUT_FILE
export outputFile=dummy
printf "Test #${COUNTER} - <hold-out %> = ${holdOut}.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile 
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1




# Test - holdOut Value check
export holdOut=0.1
export k=0
export dist=cosine
export inputFile=$INPUT_FILE
export outputFile=dummy
printf "Test #${COUNTER} - <k> = ${k}.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1





# Test - holdOut Value check
export holdOut=0.1
export k=1.5
export dist=cosine
export inputFile=$INPUT_FILE
export outputFile=dummy
printf "Test #${COUNTER} - <k> = ${k}.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1

