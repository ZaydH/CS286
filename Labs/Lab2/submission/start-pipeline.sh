#!/bin/bash

export KAFKA_DIR="/opt/kafka"

# Start the kafka server
#$KAFKA_DIR/bin/kafka-server-start.sh $KAFKA_DIR/config/server.properties &
#sleep 10 # Wait for the kafka server to start


# Create a zayd_hammoudeh topic
$KAFKA_DIR/bin/kafka-topics.sh --zookeeper localhost:5181 --create --topic zayd_hammoudeh --partitions 1 --replication-factor 1

# Verify the topic was created
$KAFKA_DIR/bin/kafka-topics.sh --zookeeper localhost:5181 --list

# Start the flume producer
/opt/mapr/flume/flume-1.6.0/bin/flume-ng agent --conf conf --conf-file kafka-flume.conf --name tier1 -Dflume.root.logger=INFO,console &
sleep 5 # Wait for the flume agent to start

