#!/bin/bash

export HADOOP_HOME=/opt/mapr/hadoop/hadoop-0.20.2
export LD_LIBRARY_PATH=$HADOOP_HOME/lib/native/Linux-amd64-64
export CLASSPATH=$HADOOP_HOME/*:$HADOOP_HOME/lib/* 
export HADOOP_CLASSPATH=$CLASSPATH

javac -d classes StdevMapper.java
javac -d classes StdevReducer.java
jar -cvf Stdev.jar -C classes/ .
javac -classpath $CLASSPATH:Stdev.jar -d classes StdevDriver.java
jar -uvf Stdev.jar -C classes/ .
