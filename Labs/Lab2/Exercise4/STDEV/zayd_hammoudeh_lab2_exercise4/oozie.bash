export OOZIE_URL='http://mapr1node:11000/oozie'
export OOZIE_HOME=/opt/mapr/oozie/oozie-4.1.0
export PATH=$PATH:$OOZIE_HOME/bin


# Put the data in the HDFS location called by the oozie flow
hadoop fs -rmr /user/user01/DATA
hadoop fs -mkdir -p /user/user01/DATA
hadoop fs -put iris-data.txt /user/user01/DATA
hadoop fs -rmr /user/user01/OUT

# Put a new copy of lab 2 exercise 4 files
hadoop fs -put lib /user/user01/
hadoop fs -put workflow.xml /user/user01/

# Run the oozie job
/opt/mapr/oozie/oozie-4.1.0/bin/oozie job -oozie="http://localhost:11000/oozie" -config /user/user01/job.properties -run


# Check job status. Replace <JobName> with the actual job name
#/opt/mapr/oozie/oozie-4.1.0/bin/oozie job -oozie http://localhost:11000/oozie -info <JobName>
#/opt/mapr/oozie/oozie-4.1.0/bin/oozie job -oozie http://localhost:11000/oozie -log <JobName>

