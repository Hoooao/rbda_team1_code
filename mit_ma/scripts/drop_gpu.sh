#!/usr/bin/bash

# Output file for Trino query
outputFile="drop_gpu.sql"
rm $outputFile

# Start the query for creating a new table
# echo "-- Creating a new table with sampled data" > $outputFile
echo "use jm9527_nyu_edu;" >> $outputFile

for i in $(seq -w 0000 0067)
do
    tableName="gpu$i"
    echo "drop table $tableName;" >> $outputFile
done

# Inform the user
echo "Trino query created in $outputFile"

