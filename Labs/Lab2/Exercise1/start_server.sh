#!/bin/bash

export KAFKA_DIR="/user/user01/LAB2/E1/KAFKA/kafka_2.10-0.8.2.2"

# Start the kafka server
$KAFKA_DIR/bin/kafka-server-start.sh $KAFKA_DIR/config/server.properties &
sleep 10 # Wait for the kafka server to start

# Create a "cs286" topic
$KAFKA_DIR/bin/kafka-topics.sh --zookeeper localhost:5181 --create --topic cs286 --partitions 1 --replication-factor 1

# Verify the topic was created
$KAFKA_DIR/bin/kafka-topics.sh --zookeeper localhost:5181 --list
