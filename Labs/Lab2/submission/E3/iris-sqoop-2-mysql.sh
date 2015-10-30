#!/bin/bash

# Create the MySQL Table
echo "Create iris_table in MySQL"
mysql -u user01 -pmapr << EOF
USE default;
DROP TABLE IF EXISTS iris_table;
CREATE TABLE iris_table (sepalLength FLOAT, sepalWidth FLOAT, petalLength FLOAT, petalWidth FLOAT, flowerID FLOAT );
SELECT * FROM iris_table;
EOF
echo "Table successfully created."

# Transfer the data from Hive to MySQL via Sqoop
echo "Copying the data from Hive to MySQL via Scoop"
sqoop export --connect jdbc:mysql://localhost/default --username root --table iris_table --export-dir /user/hive/warehouse/iris_table/ --fields-terminated-by '\t'
echo "Data transfer successful."


# Verify the table was created and the data was transferred
mysql -u user01 -pmapr << EOF
USE default;
SELECT * FROM iris_table;
SELECT COUNT(petalLength) FROM iris_table;
EOF
