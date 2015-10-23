#!/bin/bash

cd spark-zayd/examples
mvn assembly:assembly -DdescriptorId=jar-with-dependencies -Denforcer.skip=true
cd ../..
