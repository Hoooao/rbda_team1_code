#!/usr/bin/bash

hadoop fs -rm -r 0000-profiling
rm result-0000

javac -classpath `hadoop classpath` *.java

jar cvf profileGPU.jar *.class

hadoop jar profileGPU.jar ProfileGPUUsage gpu/0000 0000-profiling

hadoop fs -get 0000-profiling/part-r-00000 result-0000
