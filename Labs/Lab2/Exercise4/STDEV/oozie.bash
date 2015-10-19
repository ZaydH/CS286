#export OOZIE_URL='http://mapr1node:11000/oozie'

# Delete any existing directory for lab 2 exercise 4
hadoop fs -rmr /user/user01/lab2/exercise4/zayd_hammoudeh_lab2_exercise4
# Put a new copy of lab 2 exercise 4 files
hadoop fs -put zayd_hammoudeh_lab2_exercise4 /user/user01/lab2/exercise4/zayd_hammoudeh_lab2_exercise4

# Run the oozie job
/opt/mapr/oozie/oozie-4.1.0/bin/oozie job -oozie="http://localhost:11000/oozie" -config /user/user01/lab2/exercise4/zayd_hammoudeh_lab2_exercise4/job.properties -run
