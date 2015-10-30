export SUBMISSION_DIR=submission
export EXERCISE1_DIR=E1
export EXERCISE2_DIR=E2
export EXERCISE3_DIR=E3
export EXERCISE4_DIR=E4
export FINAL_DIRECTORY=ZAYD_HAMMOUDEH_LAB2_SUBMISSION

export DEFAULT_KAFKA_DIR=/opt/kafka
export LAB_KAFKA_DIR=/user/user01/LAB2/E1/KAFKA/kafka_2.10-0.8.2.2

# Clean any previous versions of the submission directory.
rm -rf $SUBMISSION_DIR
mkdir $SUBMISSION_DIR

# Copy the Files for Exercise 1
mkdir -p $SUBMISSION_DIR/$EXERCISE1_DIR
cp Exercise1/start_server.sh $SUBMISSION_DIR/$EXERCISE1_DIR/
cp Exercise1/start-pipeline.sh $SUBMISSION_DIR/$EXERCISE1_DIR/start-pipeline.sh
cp Exercise1/kafka-flume.conf $SUBMISSION_DIR/$EXERCISE1_DIR/kafka-flume.conf
cp Exercise1/rebuild.sh $SUBMISSION_DIR/$EXERCISE1_DIR/rebuild.sh
cp Exercise1/rerun.sh $SUBMISSION_DIR/$EXERCISE1_DIR/rerun.sh
cp /etc/rsyslog.conf $SUBMISSION_DIR/$EXERCISE1_DIR
rm -rf Exercise1/spark-zayd/examples/target > /dev/null
cp -r Exercise1/spark-zayd $SUBMISSION_DIR/$EXERCISE1_DIR


# Copy the Files for Exercise 2
mkdir -p $SUBMISSION_DIR/$EXERCISE2_DIR
cp Exercise2/syslog-flume.conf $SUBMISSION_DIR/$EXERCISE2_DIR/syslog-flume.conf
cp Exercise2/rsyslog.conf $SUBMISSION_DIR/$EXERCISE2_DIR/rsyslog.conf
cp Exercise2/flume.bash $SUBMISSION_DIR/$EXERCISE2_DIR

# Copy the files for Exercise 3
mkdir -p $SUBMISSION_DIR/$EXERCISE3_DIR
cp Exercise3/hive-iris.ql $SUBMISSION_DIR/$EXERCISE3_DIR/hive-iris.ql
cp Exercise3/iris-sqoop-2-mysql.sh $SUBMISSION_DIR/$EXERCISE3_DIR/iris-sqoop-2-mysql.sh
cp Exercise3/iris-data.txt $SUBMISSION_DIR/$EXERCISE3_DIR 
cp Exercise3/exercise3.bash $SUBMISSION_DIR/$EXERCISE3_DIR

# Copy the files for Exercise #4
mkdir -p $SUBMISSION_DIR/$EXERCISE4_DIR
#cp Exercise4/STDEV/rebuild.sh $SUBMISSION_DIR/$EXERCISE4_DIR
#cp Exercise4/STDEV/rerun.sh $SUBMISSION_DIR/$EXERCISE4_DIR/oozie_test.sh
cp -r Exercise4/STDEV/zayd_hammoudeh_lab2_exercise4/job.properties $SUBMISSION_DIR/$EXERCISE4_DIR
cp -r Exercise4/STDEV/zayd_hammoudeh_lab2_exercise4/workflow.xml $SUBMISSION_DIR/$EXERCISE4_DIR
cp Exercise4/STDEV/StdevMapper.java $SUBMISSION_DIR/$EXERCISE4_DIR
cp Exercise4/STDEV/StdevReducer.java $SUBMISSION_DIR/$EXERCISE4_DIR
cp Exercise4/STDEV/StdevDriver.java $SUBMISSION_DIR/$EXERCISE4_DIR
cp Exercise4/STDEV/rebuild.sh $SUBMISSION_DIR/$EXERCISE4_DIR
cp Exercise4/STDEV/rerun.sh $SUBMISSION_DIR/$EXERCISE4_DIR
cp Exercise4/STDEV/oozie.bash $SUBMISSION_DIR/$EXERCISE4_DIR
#cp -r Exercise4/STDEV/DATA $SUBMISSION_DIR

# Build the submission zip
cd $SUBMISSION_DIR
mkdir $FINAL_DIRECTORY
zip -r $FINAL_DIRECTORY/$EXERCISE1_DIR.zip $EXERCISE1_DIR > /dev/null
zip -r $FINAL_DIRECTORY/$EXERCISE2_DIR.zip $EXERCISE2_DIR > /dev/null
zip -r $FINAL_DIRECTORY/$EXERCISE3_DIR.zip $EXERCISE3_DIR > /dev/null
zip -r $FINAL_DIRECTORY/$EXERCISE4_DIR.zip $EXERCISE4_DIR > /dev/null
zip -r ../$FINAL_DIRECTORY.zip $FINAL_DIRECTORY > /dev/null
cd ..

# Transfer the submission zip to /user/user01 
rm -rf /user/user01/*
cp Exercise3/iris-data.txt /user/user01
cp $FINAL_DIRECTORY.zip /user/user01/
unzip /user/user01/$FINAL_DIRECTORY.zip -d /user/user01 > /dev/null
mv /user/user01/$FINAL_DIRECTORY /user/user01/LAB2_SUBMISSION
unzip /user/user01/LAB2_SUBMISSION/$EXERCISE1_DIR.zip -d /user/user01/LAB2_SUBMISSION > /dev/null 
unzip /user/user01/LAB2_SUBMISSION/$EXERCISE2_DIR.zip -d /user/user01/LAB2_SUBMISSION > /dev/null
unzip /user/user01/LAB2_SUBMISSION/$EXERCISE3_DIR.zip -d /user/user01/LAB2_SUBMISSION > /dev/null
unzip /user/user01/LAB2_SUBMISSION/$EXERCISE4_DIR.zip -d /user/user01/LAB2_SUBMISSION > /dev/null

# Make the Kafka test folder
mkdir -p $LAB_KAFKA_DIR
cp -r $DEFAULT_KAFKA_DIR/*  $LAB_KAFKA_DIR

echo "Submission building completed."
