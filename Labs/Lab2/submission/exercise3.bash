export HIVE_HOME=/opt/mapr/hive/hive-1.0/
export PATH=$PATH:$HIVE_HOME/bin

hadoop fs -mkdir -p /user/user01/

cp iris-data.txt /user/user01/

hive -f hive-iris.ql

#su root
#mysql > USE default;
#mysql > CREATE TABLE iris_table (sepalLength FLOAT NOT NULL, sepalWidth FLOAT NOT NULL, petalLength FLOAT NOT NULL, petalWidth FLOAT NOT NULL, PRIMARY KEY (id) );
#/etc/init.d/mysqld start
#mysql -u root
#mysqladmin create default;

#USE default;
#DROP TABLE iris_table;
#CREATE TABLE iris_table (sepalLength FLOAT, sepalWidth FLOAT, petalLength FLOAT, petalWidth FLOAT, flowerID FLOAT );
#select * from iris_table;

