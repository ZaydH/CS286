export KAFKA_DIR="/opt/kafka"


# Start the kafka server
$KAFKA_DIR/bin/kafka-server-start.sh $KAFKA_DIR/config/server.properties

# Create a logtest topic
$KAFKA_DIR/bin/kafka-topics.sh --zookeeper localhost:5181 --create --topic logtest --partitions 1 --replication-factor 1

# Verify the topic was created
$KAFKA_DIR/bin/kafka-topics.sh --zookeeper localhost:5181 --list

# Create a Kafka Producer
$KAFKA_DIR/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic logtest 

# Create a Kafka Consumer
$KAFKA_DIR/bin/kafka-console-consumer.sh --zookeeper localhost:5181 --topic test --from-beginning
