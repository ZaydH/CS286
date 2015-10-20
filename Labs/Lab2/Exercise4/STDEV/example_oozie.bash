export OOZIE_URL='http://mapr1node:11000/oozie'
export OOZIE_HOME=/opt/mapr/oozie/oozie-4.1.0
export PATH=$PATH:$OOZIE_HOME/bin

# Put the data in the HDFS location called by the oozie flow
hadoop fs -rmr /user/user01/map-reduce
hadoop fs -mkdir -p /user/user01/map-reduce/DATA
hadoop fs -put /home/user01/CS286/Labs/Lab2/Exercise4/STDEV/DATA/iris-data.txt /user/user01/map-reduce/DATA
mkdir -p /user/user01/map-reduce/DATA
cp /home/user01/CS286/Labs/Lab2/Exercise4/STDEV/DATA/iris-data.txt /user/user01/map-reduce/DATA/iris-data22.txt

# Put a new copy of lab 2 exercise 4 files
hadoop fs -put map-reduce /user/user01/ 

# Run the oozie job
/opt/mapr/oozie/oozie-4.1.0/bin/oozie job -oozie="http://localhost:11000/oozie" -config /user/user01/map-reduce/job.properties -run


# Check job status. Replace <JobName> with the actual job name
#/opt/mapr/oozie/oozie-4.1.0/bin/oozie job -oozie http://localhost:11000/oozie -info <JobName>
#/opt/mapr/oozie/oozie-4.1.0/bin/oozie job -oozie http://localhost:11000/oozie -log <JobName>

