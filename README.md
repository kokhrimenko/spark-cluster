# Description
Sample Spark cluster (current Spark version: 3.2.1)

# How to run
1 `build base Skark image`:
 	>> docker build -t apache-spark-base:3.2.1 .
2 Optional: build executable jar:  
	>> ``mvn clean install``  
3 Optional: copy jar file from the **/target** folder to the **/src/main/resources/apps**  
4 start up cluster:  
	>> ``docker-compose up -d --remove-orphans  --scale spark-worker=3``

# How to use
Spark UI will be available at: **http://localhost:9090/**