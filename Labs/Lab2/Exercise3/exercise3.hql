DROP TABLE IF EXISTS iris_table;

CREATE TABLE iris_table(sepalLength FLOAT, sepalWidth FLOAT, petalLength FLOAT, petalWidth FLOAT, flowerID FLOAT) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

LOAD DATA INPATH '/user/user01/lab2/exercise3/DATA/iris-data.txt' OVERWRITE INTO TABLE iris_table;

SELECT MAX(sepalLength) as maxSepalLength from iris_table;
SET maxSepalLength;
SELECT MIN(sepalLength) as minSepalLength from iris_table;
SET minSepalLength;
SELECT AVG(sepalLength) as meanSepalLength from iris_table;
SET meanSepalLength;
