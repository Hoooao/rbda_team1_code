#!/usr/bin/bash

hadoop fs -rm -r clean-profiling
rm result_clean

javac -classpath `hadoop classpath` *.java

jar cvf profileGPU.jar *.class

hadoop jar profileGPU.jar ProfileGPUUsage clean-data clean-profiling

hadoop fs -get clean-profiling/part-r-00000 result_clean
