#!/usr/bin/bash

hadoop fs -rm -r cpu-timeseries

javac -classpath `hadoop classpath` *.java

jar cvf cpuTimeseries.jar *.class

hadoop jar cpuTimeseries.jar CleanCPUTimeseries dcc-data/202201/cpu/ cpu-timeseries
