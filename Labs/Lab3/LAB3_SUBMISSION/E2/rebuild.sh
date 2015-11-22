#!/bin/bash

export JAR_NAME="knn.jar"

echo "Create the classes directory for the java class files."
rm -irf classes &> /dev/null
mkdir classes &> /dev/null

echo "Building the JAR file."
javac -d classes PointSet.java 
jar cvmf MANIFEST.MF $JAR_NAME -C classes/ . 
javac -classpath $CLASSPATH:$JAR_NAME -d classes Knn.java 
jar -uvf $JAR_NAME -C classes/ . 
echo "Building complete."
