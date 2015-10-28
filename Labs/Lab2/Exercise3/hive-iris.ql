-- Simple Function to find the min, max, and mean for the iris data.

DROP TABLE IF EXISTS iris_table;

CREATE TABLE iris_table(sepalLength FLOAT, sepalWidth FLOAT, petalLength FLOAT, petalWidth FLOAT, flowerID FLOAT) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

LOAD DATA INPATH '/user/user01/iris-data.txt' OVERWRITE INTO TABLE iris_table;

--INSERT OVERWRITE LOCAL DIRECTORY '/user/user01/iris_hive_out/max' SELECT MAX(petalLength) as maxSepalLength FROM iris_table;
--SET maxSepalLength;
--INSERT OVERWRITE LOCAL DIRECTORY '/user/user01/iris_hive_out/min' SELECT MIN(petalLength) as minSepalLength FROM iris_table;
--SET minSepalLength;
INSERT OVERWRITE LOCAL DIRECTORY '/user/user01/iris_hive_out/' SELECT AVG(petalLength) AS avgSepalLength FROM iris_table WHERE flowerID < 0.5;
--SET avgSepalLength;
