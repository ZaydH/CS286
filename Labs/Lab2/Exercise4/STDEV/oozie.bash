#export OOZIE_URL='http://mapr1node:11000/oozie'

# Put the data in the HDFS location called by the oozie flow
hadoop fs -rmr /user/user01/lab2/exercise4/DATA
hadoop fs -mkdir /user/user01/lab2/exercise4/DATA
hadoop fs -put DATA/data.txt /user/user01/lab2/exercise4/DATA

# Delete any existing directory for lab 2 exercise 4
hadoop fs -rmr /user/user01/lab2/exercise4/zayd_hammoudeh_lab2_exercise4
# Put a new copy of lab 2 exercise 4 files
hadoop fs -put zayd_hammoudeh_lab2_exercise4 /user/user01/lab2/exercise4/zayd_hammoudeh_lab2_exercise4

# Run the oozie job
/opt/mapr/oozie/oozie-4.1.0/bin/oozie job -oozie="http://localhost:11000/oozie" -config /user/user01/lab2/exercise4/zayd_hammoudeh_lab2_exercise4/job.properties -run


# Check job status. Replace <JobName> with the actual job name
#/opt/mapr/oozie/oozie-4.1.0/bin/oozie job -oozie http://localhost:11000/oozie -info <JobName>
#/opt/mapr/oozie/oozie-4.1.0/bin/oozie job -oozie http://localhost:11000/oozie -log <JobName>

