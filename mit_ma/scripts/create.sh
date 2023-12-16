#!/usr/bin/bash

# Output file
outputFile="create_tables.sql"

# Start with an empty file
> $outputFile

# Loop from 1 to 69
for i in $(seq -w 0001 0069)
do
    # Construct the table name and location
    tableName="gpu$i"
    location="/user/jm9527_nyu_edu/gpu/$i"

    # Append the HiveQL command to the file
    echo "CREATE EXTERNAL TABLE $tableName (index int, gpu_pct double, gpu_mem_pct double, mem_free double, mem_used double) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LOCATION '$location';" >> $outputFile
done
