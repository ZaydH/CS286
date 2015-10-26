export SUBMISSION_DIR="submission"

# Clean any previous versions of the submission directory.
rm -rf $SUBMISSION_DIR
mkdir $SUBMISSION_DIR

# Copy the Files for Exercise 1
cp Exercise1/start-pipeline.sh $SUBMISSION_DIR/start-pipeline.sh
cp Exercise1/kafka-flume.conf $SUBMISSION_DIR/kafka-flume.conf
cp Exercise1/rebuild.sh $SUBMISSION_DIR/rebuild.sh
cp Exercise1/rerun.sh $SUBMISSION_DIR/rerun.sh
rm -rf Exercise1/spark-zayd/examples/target > /dev/null
cp -r Exercise1/spark-zayd $SUBMISSION_DIR


# Copy the Files for Exercise 2
cp Exercise2/syslog-flume.conf $SUBMISSION_DIR/syslog-flume.conf
cp Exercise2/rsyslog.conf $SUBMISSION_DIR/rsyslog.conf
cp Exercise2/flume.bash $SUBMISSION_DIR

# Copy the files for Exercise 3
cp Exercise3/hive-iris.ql $SUBMISSION_DIR/hive-iris.ql
cp Exercise3/iris-sqoop-2-mysql.sh $SUBMISSION_DIR/iris-sqoop-2-mysql.sh
cp Exercise3/iris-data.txt /user/user01
cp Exercise3/exercise3.bash $SUBMISSION_DIR

# Copy the files for Exercise #4
#cp Exercise4/STDEV/rebuild.sh $SUBMISSION_DIR
cp Exercise4/STDEV/rerun.sh $SUBMISSION_DIR/oozie_test.sh
cp -r Exercise4/STDEV/zayd_hammoudeh_lab2_exercise4/* $SUBMISSION_DIR
cp -r Exercise4/STDEV/DATA $SUBMISSION_DIR

# Builz the submission zip
cd $SUBMISSION_DIR
zip -r zayd_hammoudeh_lab2.zip . > /dev/null
mv zayd_hammoudeh_lab2.zip ..
cd ..

rm -rf /user/user01/*
cp -r $SUBMISSION_DIR/* /user/user01/

echo "Submission building completed."
