#!/bin/bash

export JAR_NAME="kmeans.jar"

echo "Create the classes directory for the java class files."
rm -rf classes &> /dev/null
mkdir classes &> /dev/null

echo "Building the JAR file."
javac -d classes PointSet.java 
jar cvmf MANIFEST.MF $JAR_NAME -C classes/ . 
javac -classpath $CLASSPATH:$JAR_NAME -d classes Kmeans.java 
jar -uvf $JAR_NAME -C classes/ . 
echo "Building complete."
