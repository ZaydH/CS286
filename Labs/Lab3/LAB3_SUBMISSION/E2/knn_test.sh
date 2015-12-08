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
export holdOut=10
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
export holdOut=10
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
export holdOut=4.9
export k=3
export dist=cosine
export inputFile=$INPUT_FILE
export outputFile=dummy
printf "Test #${COUNTER} - <hold-out> = ${holdOut}.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1




# Test - holdOut Value check
export holdOut=20.1
export k=3
export dist=cosine
export inputFile=$INPUT_FILE
export outputFile=dummy
printf "Test #${COUNTER} - <hold-out> = ${holdOut}.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile 
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1




# Test - k Value check
export holdOut=10
export k=0
export dist=cosine
export inputFile=$INPUT_FILE
export outputFile=dummy
printf "Test #${COUNTER} - Invalid k: <k> = ${k}.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1





# Test - k Value check
export holdOut=10
export k=1.5
export dist=cosine
export inputFile=$INPUT_FILE
export outputFile=dummy
printf "Test #${COUNTER} - Invalid k: <k> = ${k}.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1





# Test - k Value check
export holdOut=20
export k=121
export dist=cosine
export inputFile=$INPUT_FILE
export outputFile=dummy
printf "Test #${COUNTER} - k more than training set: <k> = ${k}.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1





# Test - Input File Check
export holdOut=10.1
export k=2
export dist=cosine
export inputFile=dummy
export outputFile=dummy
printf "Test #${COUNTER} - Input File Does Not Exist: <inputFile> = ${inputFile}.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1





# Test - Output File Check
export holdOut=10.1
export k=2
export dist=cosine
export inputFile=$INPUT_FILE
export outputFile=$INPUT_FILE
printf "Test #${COUNTER} - Output File Exists: <inputFile> = <outputFile> = ${outputFile}.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1





# Test - Distance Metric Check
export holdOut=10.1
export k=2
export dist=dummy
export inputFile=$INPUT_FILE
export outputFile=dummy
printf "Test #${COUNTER} - Invalid Distance Metric: <dist> = ${dist}.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1




# Test - Single Neighbor, Cosine
export holdOut=10.1
export k=1
export dist=cosine
export inputFile=$INPUT_FILE
export outputFileName="${holdOut}_${k}_${dist}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - holdOut=\"${holdOut}\", k=\"${k}\", dist=\"${dist}\" test.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1




# Test - Single Neighbor, Euclidean
export holdOut=10.1
export k=1
export dist=euclidean
export inputFile=$INPUT_FILE
export outputFileName="${holdOut}_${k}_${dist}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - holdOut=\"${holdOut}\", k=\"${k}\", dist=\"${dist}\" test.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1




# Test - Five Neighbors, Euclidean
export holdOut=5.01
export k=5
export dist=euclidean
export inputFile=$INPUT_FILE
export outputFileName="${holdOut}_${k}_${dist}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - holdOut=\"${holdOut}\", k=\"${k}\", dist=\"${dist}\" test.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1




# Test - Five Neighbors, Cosine
export holdOut=5.01
export k=5
export dist=cosine
export inputFile=$INPUT_FILE
export outputFileName="${holdOut}_${k}_${dist}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - holdOut=\"${holdOut}\", k=\"${k}\", dist=\"${dist}\" test.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1




# Test - 10 Neighbors, Cosine
export holdOut=20
export k=10
export dist=cosine
export inputFile=$INPUT_FILE
export outputFileName="${holdOut}_${k}_${dist}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - holdOut=\"${holdOut}\", k=\"${k}\", dist=\"${dist}\" test.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1




# Test - 10 Neighbors, Euclidean
export holdOut=20
export k=10
export dist=euclidean
export inputFile=$INPUT_FILE
export outputFileName="${holdOut}_${k}_${dist}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - holdOut=\"${holdOut}\", k=\"${k}\", dist=\"${dist}\" test.\n"
bash rerun.sh $holdOut $k $dist $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1

