#!/bin/bash

export KAFKA_DIR="/user/user01/LAB2/E1/KAFKA/kafka_2.10-0.8.2.2"

# Start the flume producer
/opt/mapr/flume/flume-1.6.0/bin/flume-ng agent --conf conf --conf-file kafka-flume.conf --name tier1 -Dflume.root.logger=INFO,console 
sleep 5 # Wait for the flume agent to start
