#!/usr/bin/bash

hadoop fs -rm -r gpu-sampled-combined

javac -classpath `hadoop classpath` *.java

jar cvf combineGPU.jar *.class

hadoop jar combineGPU.jar CombineGPUUsage gpu-sampled gpu-sampled-combined

# hadoop fs -get gpu-profiling/part-r-00000 result
