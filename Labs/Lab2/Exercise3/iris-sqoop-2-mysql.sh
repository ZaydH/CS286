sqoop export --connect jdbc:mysql://localhost/default --username root --table iris_table --export-dir /user/hive/warehouse/iris_table/ --fields-terminated-by '\t'
