#!/bin/bash

rm spark-zayd/examples/src/main/scala/org/apache/spark/examples/mllib/StreamingTestExample.scala

cd spark-zayd/examples
mvn assembly:assembly -DdescriptorId=jar-with-dependencies -Denforcer.skip=true
cd ../..
