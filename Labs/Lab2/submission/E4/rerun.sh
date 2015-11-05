#!/bin/bash

export HADOOP_HOME=/opt/mapr/hadoop/hadoop-0.20.2
export LD_LIBRARY_PATH=$HADOOP_HOME/lib/native/Linux-amd64-64
export CLASSPATH=$HADOOP_HOME/lib/*:$HADOOP_HOME/*
export HADOOP_CLASSPATH=$CLASSPATH

rm -rf /user/user01/oozie_iris_stdev
hadoop jar Iris.jar Stdev.StdevDriver file:///user/user01/iris-data.txt /user/user01/oozie_iris_stdev 
