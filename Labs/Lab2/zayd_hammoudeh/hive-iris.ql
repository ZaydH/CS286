-- Simple Function to find the min, max, and mean for the iris data.

DROP TABLE IF EXISTS iris_table;

CREATE TABLE iris_table(sepalLength FLOAT, sepalWidth FLOAT, petalLength FLOAT, petalWidth FLOAT, flowerID FLOAT) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

LOAD DATA INPATH '/user/user01/iris-data.txt' OVERWRITE INTO TABLE iris_table;

INSERT OVERWRITE LOCAL DIRECTORY '/user/user01/iris_hive_out/max' SELECT MAX(sepalLength) as maxSepalLength from iris_table;
--SET maxSepalLength;
INSERT OVERWRITE LOCAL DIRECTORY '/user/user01/iris_hive_out/min' SELECT MIN(sepalLength) as minSepalLength from iris_table;
--SET minSepalLength;
INSERT OVERWRITE LOCAL DIRECTORY '/user/user01/iris_hive_out/avg' SELECT AVG(sepalLength) as avgSepalLength from iris_table;
--SET avgSepalLength;
