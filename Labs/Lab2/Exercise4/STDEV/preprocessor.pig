-- preprocessor.pig - A simple pig script for processing some file.

--pig -x local - Command to run pig instructions from the command line
records = LOAD '/user/user01/Lab2/Exercise4/DATA/iris-data.txt' AS (sepalLength:float, sepalWidth:float, petalLength:float, petalWidth:float, flowerID:float);

filteredRecords = FILTER records BY flowerID < 0.5;

filteredSepalLength = FOREACH filteredRecords GENERATE sepalLength;

STORE filteredSepalLength INTO '/user/user01/Lab2/Exercise4/OUT/filteredData.txt';
