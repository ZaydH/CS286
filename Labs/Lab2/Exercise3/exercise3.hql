DROP TABLE iris_table;

CREATE TABLE iris_table(sepalLength FLOAT, sepalWidth FLOAT, petalLength FLOAT, petalWidth FLOAT, flowerID FLOAT);

LOAD DATA INPATH '/user/user01/lab2/exercise3/DATA/iris-data.txt' OVERWRITE INTO TABLE iris_table;


