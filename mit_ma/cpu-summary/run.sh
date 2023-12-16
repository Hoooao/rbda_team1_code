#!/usr/bin/bash

# hadoop fs -rm -r clean-data

javac -classpath `hadoop classpath` *.java

jar cvf filterCPU.jar *.class

hadoop jar filterCPU.jar FilterCPUUsage summary cpu-summary-filtered

hadoop fs -cat cpu-summary-filtered/0000/part-m-00000 > result
