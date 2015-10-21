#!/bin/bash

/opt/mapr/spark/spark-1.4.1/bin/spark-submit --class org.apache.spark.examples.streaming.JavaKafkaWordCount --master local[2] spark-zayd/examples/target/spark-examples_2.10-1.6.0-SNAPSHOT-jar-with-dependencies.jar localhost:5181 default logtest 1
