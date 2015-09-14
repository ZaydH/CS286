#!/bin/bash

export HADOOP_HOME=/opt/mapr/hadoop/hadoop-0.20.2
export LD_LIBRARY_PATH=$HADOOP_HOME/lib/native/Linux-amd64-64
export CLASSPATH=$HADOOP_HOME/lib/*:$HADOOP_HOME/*
export HADOOP_CLASSPATH=$CLASSPATH

rm -rf /user/$USER/IRIS_LAB/OUT
hadoop jar Iris.jar Iris.IrisDriver file:///home/$USER/CS286/Labs/Lab1/data/iris-data.txt /user/$USER/IRIS_LAB/OUT

echo
echo
echo "HERE'S THE RESULTS..." 
cat /user/$USER/IRIS_LAB/OUT/part-r-00000
