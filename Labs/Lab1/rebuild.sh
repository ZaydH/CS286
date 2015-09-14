#!/bin/bash

export HADOOP_HOME=/opt/mapr/hadoop/hadoop-0.20.2
export LD_LIBRARY_PATH=$HADOOP_HOME/lib/native/Linux-amd64-64
export CLASSPATH=$HADOOP_HOME/*:$HADOOP_HOME/lib/* 
export HADOOP_CLASSPATH=$CLASSPATH

mkdir -p bin
rm -rf Iris.jar
javac -d bin ./src/Iris/IrisMapper.java
javac -d bin ./src/Iris/IrisReducer.java
jar -cvf Iris.jar -C bin/ .
javac -classpath $CLASSPATH:Iris.jar -d bin ./src/Iris/IrisDriver.java
jar -uvf Iris.jar -C bin/ .
