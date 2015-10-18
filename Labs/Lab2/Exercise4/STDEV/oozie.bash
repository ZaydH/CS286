export OOZIE_URL='http://mapr1node:11000/oozie'

hadoop fs -put zayd_hammoudeh_lab2_exercise4 /user/user01/lab2/exercise4/zayd_hammoudeh_lab2_exercise4

oozie job -oozie exercise4-oozie.xml 
