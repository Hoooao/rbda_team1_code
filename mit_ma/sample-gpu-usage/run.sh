#!/usr/bin/bash

# hadoop fs -rm -r clean-data

javac -classpath `hadoop classpath` *.java

jar cvf sampleGPU.jar *.class

for i in $(seq -w 0000 0069)
do
    echo $i
    hadoop jar sampleGPU.jar SampleGPUUsage dcc-data/202201/gpu/$i/ gpu-sampled/$i/ 0.5
    # hadoop jar filterGPU.jar FilterGPUUsage-$i dcc-data/202201/gpu/$i/ clean-data/$i/
done 

# hadoop fs -cat gpu/0000/part-m-00000 > result
