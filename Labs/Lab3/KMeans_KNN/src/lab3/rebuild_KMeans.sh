#!/bin/bash

export JAR_NAME="kmeans.jar"

echo "Create the classes directory for the java class files."
rm -irf classes &> /dev/null
mkdir classes &> /dev/null

echo "Build the JAR file."
javac -d classes PointSet.java
jar -cvf $JAR_NAME -C classes/ .
javac -classpath $CLASSPATH:$JAR_NAME -d classes KMeans.java
jar -uvfm $JAR_NAME MANIFEST.MF -C classes/ .

