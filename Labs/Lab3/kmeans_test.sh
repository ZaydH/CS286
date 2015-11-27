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
printf "Building the KMeans JAR file.\n"
bash rebuild.sh &> /dev/null
printf "KMeans JAR file built.\n\n\n"

# Create a new test results folder
rm -rf $OUT_DIR &> /dev/null
mkdir $OUT_DIR

# Initialize the counter
COUNTER=1

# Do an extra input parameter check
printf "Test #${COUNTER} - Too many parameter check.\n"
export k=3
export maxIterations=1
export delta=1
export dist=cosine
export init=random
export inputFile=$INPUT_FILE
export outputFile=dummy
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile extra
if [ $? -eq 0 ]; then
	printf "ERROR: Returned value 0."
else
	printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1

# Perform insufficient parameter count check
printf "Test #${COUNTER} - Insufficient parameter check.\n"
export k=3
export maxIterations=1
export delta=1
export dist=cosine
export init=random
export inputFile=$INPUT_FILE
export outputFile=dummy
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile  
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1


# Test #03 - Perform invalid value of k test
export k=0
export maxIterations=1
export delta=1
export dist=cosine
export init=random
export inputFile=$INPUT_FILE
export outputFile=dummy.txt

printf "Test #${COUNTER} - k = ${k} test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1




# Test #04 - Perform invalid value of max iterations test
export k=4
export maxIterations=0
export delta=1
export dist=cosine
export init=random
export inputFile=$INPUT_FILE
export outputFile=dummy.txt

printf "Test #${COUNTER} - max-iterations = ${maxIterations} test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1




# Test #05 - Delta = 0 Test
export k=4
export maxIterations=50
export delta=0
export dist=cosine
export init=random
export inputFile=$INPUT_FILE
export outputFile=dummy.txt

printf "Test #${COUNTER} - delta = ${delta} test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1




# Test #06 - Delta = -1 Test
export k=4
export maxIterations=50
export delta=-1
export dist=cosine
export init=random
export inputFile=$INPUT_FILE
export outputFile=dummy.txt

printf "Test #${COUNTER} - delta = ${delta} test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1



# Test #07 - Invalid distance metric test
export k=4
export maxIterations=50
export delta=3
export dist=dummy
export init=random
export inputFile=$INPUT_FILE
export outputFile=dummy.txt

printf "Test #${COUNTER} - dist = ${dist} test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1




# Test #08 - Invalid centroid initialization test
export k=4
export maxIterations=50
export delta=3
export dist=cosine
export init=dummy
export inputFile=$INPUT_FILE
export outputFile=dummy.txt

printf "Test #${COUNTER} - init = ${init} test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1




# Test #09 - Invalid input file
export k=4
export maxIterations=50
export delta=3
export dist=cosine
export init=random
export inputFile=dummyFile
export outputFile=dummy.txt

printf "Test #${COUNTER} - inputFile = ${INPUT_FILE} test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1




# Test #10 - Invalid output file
export k=4
export maxIterations=50
export delta=3
export dist=cosine
export init=random
export inputFile=$INPUT_FILE
export outputFile=$INPUT_FILE

printf "Test #${COUNTER} - inputFile = outputFile = ${INPUT_FILE} test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -eq 0 ]; then
        printf "ERROR: Returned value 0."
else
        printf "PASSED"
fi
printf "\n\n\n"
let COUNTER=COUNTER+1




# Test - High Delta, One Iteration, Cosine Distance, Partition Test
export k=4
export maxIterations=1
export delta=600
export dist=cosine
export init=partition
export inputFile=$INPUT_FILE
export outputFileName="${k}_${maxIterations}_${delta}_${dist}_${init}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - k=\"${k}\", maxitr=\"${maxIterations}\", delta=\"${delta}\", dist=\"${dist}\", init=\"${init}\" test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
	diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1




# Test - High Delta, One Iteration, Euclidean Distance, Partition Test
export k=4
export maxIterations=1
export delta=600
export dist=euclidean
export init=partition
export inputFile=$INPUT_FILE
export outputFileName="${k}_${maxIterations}_${delta}_${dist}_${init}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - k=\"${k}\", maxitr=\"${maxIterations}\", delta=\"${delta}\", dist=\"${dist}\", init=\"${init}\" test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1




# Test - High Delta, One Iteration, Cosine Distance, Zero Test
export k=4
export maxIterations=1
export delta=600
export dist=cosine
export init=zero
export inputFile=$INPUT_FILE
export outputFileName="${k}_${maxIterations}_${delta}_${dist}_${init}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - k=\"${k}\", maxitr=\"${maxIterations}\", delta=\"${delta}\", dist=\"${dist}\", init=\"${init}\" test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1



# Test - High Delta, One Iteration, Euclidean Distance, Zero Test
export k=4
export maxIterations=1
export delta=600
export dist=euclidean
export init=zero
export inputFile=$INPUT_FILE
export outputFileName="${k}_${maxIterations}_${delta}_${dist}_${init}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - k=\"${k}\", maxitr=\"${maxIterations}\", delta=\"${delta}\", dist=\"${dist}\", init=\"${init}\" test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1




# Test - Low Delta Cosine Distance Test
export k=5
export maxIterations=50
export delta=0.1
export dist=cosine
export init=zero
export inputFile=$INPUT_FILE
export outputFileName="${k}_${maxIterations}_${delta}_${dist}_${init}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - k=\"${k}\", maxitr=\"${maxIterations}\", delta=\"${delta}\", dist=\"${dist}\", init=\"${init}\" test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1




# Test - Low Delta, Euclidean Distance Test
export k=5
export maxIterations=50
export delta=0.1
export dist=euclidean
export init=zero
export inputFile=$INPUT_FILE
export outputFileName="${k}_${maxIterations}_${delta}_${dist}_${init}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - k=\"${k}\", maxitr=\"${maxIterations}\", delta=\"${delta}\", dist=\"${dist}\", init=\"${init}\" test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1




# Test - Low Delta, Euclidean Distance Test
export k=5
export maxIterations=50
export delta=0.1
export dist=euclidean
export init=random
export inputFile=$INPUT_FILE
export outputFileName="${k}_${maxIterations}_${delta}_${dist}_${init}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - k=\"${k}\", maxitr=\"${maxIterations}\", delta=\"${delta}\", dist=\"${dist}\", init=\"${init}\" test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
fi
printf "\n\n"
let COUNTER=COUNTER+1




# Test - Low Delta, Euclidean Distance Test
export k=5
export maxIterations=50
export delta=0.1
export dist=euclidean
export init=partition
export inputFile=$INPUT_FILE
export outputFileName="${k}_${maxIterations}_${delta}_${dist}_${init}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - k=\"${k}\", maxitr=\"${maxIterations}\", delta=\"${delta}\", dist=\"${dist}\", init=\"${init}\" test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1




# Test - One cluster Test
export k=1
export maxIterations=50
export delta=5
export dist=euclidean
export init=partition
export inputFile=$INPUT_FILE
export outputFileName="${k}_${maxIterations}_${delta}_${dist}_${init}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - k=\"${k}\", maxitr=\"${maxIterations}\", delta=\"${delta}\", dist=\"${dist}\", init=\"${init}\" test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1




# Test - One cluster Test
export k=1
export maxIterations=50
export delta=5
export dist=cosine
export init=partition
export inputFile=$INPUT_FILE
export outputFileName="${k}_${maxIterations}_${delta}_${dist}_${init}.txt"
export outputFile="${OUT_DIR}/${outputFileName}"

printf "Test #${COUNTER} - k=\"${k}\", maxitr=\"${maxIterations}\", delta=\"${delta}\", dist=\"${dist}\", init=\"${init}\" test.\n"
bash rerun.sh $k $maxIterations $delta $dist $init $inputFile $outputFile
if [ $? -ne 0 ]; then
        printf "ERROR: Did not return value 0.\n"
else
        printf "PASSED\n"
        diff "${COMPARE_DIR}/${outputFileName}" "${outputFile}"
fi
printf "\n\n"
let COUNTER=COUNTER+1
