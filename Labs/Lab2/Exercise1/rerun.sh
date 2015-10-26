#!/bin/bash


# Create a zayd_hammoudeh topic
$KAFKA_DIR/bin/kafka-topics.sh --zookeeper localhost:5181 --create --topic zayd_hammoudeh --partitions 1 --replication-factor 1

# Verify the topic was created
$KAFKA_DIR/bin/kafka-topics.sh --zookeeper localhost:5181 --list

/opt/mapr/spark/spark-1.4.1/bin/spark-submit --class org.apache.spark.examples.streaming.JavaKafkaWordCount --master local[2] spark-zayd/examples/target/spark-examples_2.10-1.6.0-SNAPSHOT-jar-with-dependencies.jar localhost:5181 default zayd_hammoudeh 1
