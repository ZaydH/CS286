#!/bin/bash

export HADOOP_HOME=/opt/mapr/hadoop/hadoop-0.20.2
export LD_LIBRARY_PATH=$HADOOP_HOME/lib/native/Linux-amd64-64
export CLASSPATH=$HADOOP_HOME/lib/*:$HADOOP_HOME/*
export HADOOP_CLASSPATH=$CLASSPATH

rm -rf /user/$USER/lab2/exercise4/OUT
hadoop jar zayd_hammoudeh_lab2_exercise4/lib/Stdev.jar Stdev.StdevDriver file:///home/$USER/CS286/Labs/Lab2/Exercise4/STDEV/DATA/iris-data.txt /user/$USER/lab2/exercise4/OUT

hadoop fs -cat /user/$USER/lab2/exercise4/OUT/part-r-00000
