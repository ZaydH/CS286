#!/bin/bash

export HADOOP_HOME=/opt/mapr/hadoop/hadoop-0.20.2
export LD_LIBRARY_PATH=$HADOOP_HOME/lib/native/Linux-amd64-64
export CLASSPATH=$HADOOP_HOME/*:$HADOOP_HOME/lib/* 
export HADOOP_CLASSPATH=$CLASSPATH

export JAR_NAME=Iris

mkdir classes
javac -d classes StdevMapper.java
javac -d classes StdevReducer.java
jar -cvf $JAR_NAME.jar -C classes/ .
javac -classpath $CLASSPATH:$JAR_NAME.jar -d classes StdevDriver.java
jar -uvf $JAR_NAME.jar -C classes/ .

#cp Iris.jar zayd_hammoudeh_lab2_exercise4/lib
