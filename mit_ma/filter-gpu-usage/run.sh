#!/usr/bin/bash

# hadoop fs -rm -r clean-data

javac -classpath `hadoop classpath` *.java

jar cvf filterGPU.jar *.class

for i in $(seq -w 0000 0069)
do
    hadoop jar filterGPU.jar FilterGPUUsage dcc-data/202201/gpu/$i/ gpu/$i/
    # hadoop jar filterGPU.jar FilterGPUUsage-$i dcc-data/202201/gpu/$i/ clean-data/$i/
done 

# hadoop fs -cat gpu/0000/part-m-00000 > result
