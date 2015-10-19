export HIVE_HOME=/opt/mapr/hive/hive-1.0/
export PATH=$PATH:$HIVE_HOME/bin

hadoop fs -mkdir -p /user/user01/

cp iris-data.txt /user/user01/

hive -f hive-iris.ql

#su root
#mysql > USE default;
#mysql > CREATE TABLE iris_table (id INT NOT NULL AUTO_INCREMENT, sepalLength FLOAT NOT NULL, sepalWidth FLOAT NOT NULL, petalLength FLOAT NOT NULL, petalWidth FLOAT NOT NULL, PRIMARY KEY (id) );


