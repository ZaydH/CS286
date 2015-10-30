export SUBMISSION_DIR=submission
export EXERCISE1_DIR=E1
export EXERCISE2_DIR=E2
export EXERCISE3_DIR=E3
export EXERCISE4_DIR=E4

export DEFAULT_KAFKA_DIR=/opt/kafka
export LAB_KAFKA_DIR=/user/user01/LAB2/E1/KAFKA/kafka_2.10-0.8.2.2

# Clean any previous versions of the submission directory.
rm -rf $SUBMISSION_DIR
mkdir $SUBMISSION_DIR

# Copy the Files for Exercise 1
mkdir -p $SUBMISSION_DIR/$EXERCISE1_DIR
cp Exercise1/start-pipeline.sh $SUBMISSION_DIR/$EXERCISE1_DIR/start-pipeline.sh
cp Exercise1/kafka-flume.conf $SUBMISSION_DIR/$EXERCISE1_DIR/kafka-flume.conf
cp Exercise1/rebuild.sh $SUBMISSION_DIR/$EXERCISE1_DIR/rebuild.sh
cp Exercise1/rerun.sh $SUBMISSION_DIR/$EXERCISE1_DIR/rerun.sh
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
#cp Exercise4/STDEV/rebuild.sh $SUBMISSION_DIR
#cp Exercise4/STDEV/rerun.sh $SUBMISSION_DIR/oozie_test.sh
cp -r Exercise4/STDEV/zayd_hammoudeh_lab2_exercise4/* $SUBMISSION_DIR
cp Exercise4/STDEV/StdevMapper.java $SUBMISSION_DIR
cp Exercise4/STDEV/StdevReducer.java $SUBMISSION_DIR
#cp -r Exercise4/STDEV/DATA $SUBMISSION_DIR

mkdir 

# Build the submission zip
cd $SUBMISSION_DIR
zip -r $EXERCISE1_DIR.zip $EXERCISE1_DIR > /dev/null
zip -r $EXERCISE2_DIR.zip $EXERCISE2_DIR > /dev/null
zip -r $EXERCISE3_DIR.zip $EXERCISE3_DIR > /dev/null
zip -r $EXERCISE4_DIR.zip $EXERCISE4_DIR > /dev/null
cd ..

rm -rf /user/user01/*
cp -r $SUBMISSION_DIR/* /user/user01/
mkdir -p $LAB_KAFKA_DIR
cp -r $DEFAULT_KAFKA_DIR/*  $LAB_KAFKA_DIR

echo "Submission building completed."
