#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <filename>"
    exit 1
fi

filename="$1"

# user should provide a list of node_id to be queried
if [ ! -f "$filename" ]; then
    echo "File not found: $filename"
    exit 2
fi
counter=0

while IFS= read -r line
do
    line=$(echo "$line" | sed 's/^"//;s/"$//')
    echo "$line"
    echo $counter
    presto --server http://localhost:8060 --catalog hive --schema nodes --execute "select time_stamp,memory_utilization from nodes where nodeid='$line' order by time_stamp;" > ./mem/$counter.txt
    ((counter++))
done < "$filename"
