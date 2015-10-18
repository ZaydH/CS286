export HIVE_HOME=/opt/mapr/hive/hive-1.0/
export PATH=$PATH:$HIVE_HOME/bin

hadoop fs -mkdir -p /user/user01/lab2/exercise3/DATA

cp iris-data.txt /user/user01/lab2/exercise3/DATA

hive -f exercise3.hql

mysql > DROP DATABASE IF EXISTS zayd_hammoudeh;
mysql > CREATE DATABASE zayd_hammoudeh;

