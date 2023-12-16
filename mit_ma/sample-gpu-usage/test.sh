#!/usr/bin/bash

hadoop fs -rm -r tmp

javac -classpath `hadoop classpath` *.java

jar cvf sampleGPU.jar *.class

hadoop jar sampleGPU.jar SampleGPUUsage dcc-data/202201/gpu/0000/ tmp 0.5

# hadoop fs -cat gpu/0000/part-m-00000 > result
