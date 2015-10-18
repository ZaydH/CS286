-- preprocessor.pig - A simple pig script for processing some file.

--pig - Command to run pig instructions from the command line
--pig -x local filename.pg - Command to run a script named "filename.pig"

records = LOAD '/user/user01/Lab2/Exercise4/DATA/iris-data.txt' AS (sepalLength:float, sepalWidth:float, petalLength:float, petalWidth:float, flowerID:float);

filteredRecords = FILTER records BY flowerID < 0.5;

filteredSepalLength = FOREACH filteredRecords GENERATE sepalLength;

rmf /user/user01/Lab2/Exercise4/DATA/FILTERED
STORE filteredSepalLength INTO '/user/user01/Lab2/Exercise4/DATA/FILTERED';
