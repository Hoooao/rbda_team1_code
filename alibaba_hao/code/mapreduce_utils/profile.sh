# Assume data is uploaded to HDFS

# Get distribution of Nodes' usage of memory and CPU
javac -classpath `hadoop classpath` *.java
jar cvf nodeUsage.jar.jar *.class
hadoop jar nodeUsage.jar NodeUsage rbda_proj/cluster-trace-microservices-v2022/data/NodeMetrics/NodeMetricsUpdate_0.csv rbda_proj/output_mem mem
hadoop jar nodeUsage.jar NodeUsage rbda_proj/cluster-trace-microservices-v2022/data/NodeMetrics/NodeMetricsUpdate_0.csv rbda_proj/output_cpu cpu

# Get distribution of Microservices; usage of memory and CPU
javac -classpath `hadoop classpath` *.java
jar cvf msUsage.jar *.class
hadoop jar msUsage.jar MSUsage rbda_proj/cluster-trace-microservices-v2022/data/MSMetrics/ rbda_proj/output_mem_ms mem
hadoop jar msUsage.jar MSUsage rbda_proj/cluster-trace-microservices-v2022/data/MSMetrics/ rbda_proj/output_cpu_ms cpu


# Remove unused fields 
javac -classpath `hadoop classpath` *.java
jar cvf cleanMSCG.jar *.class
hadoop jar cleanMSCG.jar CleanMSCG rbda_proj/cluster-trace-microservices-v2022/data/CallGraph/ rbda_proj/output_cleanMSCG