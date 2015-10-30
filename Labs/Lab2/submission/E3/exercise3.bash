export HIVE_HOME=/opt/mapr/hive/hive-1.0/
export PATH=$PATH:$HIVE_HOME/bin

hive -f hive-iris.ql

echo "Print the mean petal length for flowerID \"0.0\"."
hadoop fs -cat /user/user01/iris_hive_out/000000_0


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

