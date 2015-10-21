#!/bin/bash

cd zayd-spark
mvn assembly:assembly -DdescriptorId=jar-with-dependencies -Denforcer.skip=true
cd ..
