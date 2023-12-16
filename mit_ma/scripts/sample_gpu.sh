#!/usr/bin/bash

# Output file for Trino query
outputFile="aggregate_sampled_query.sql"
rm $outputFile

# Start the query for creating a new table
# echo "-- Creating a new table with sampled data" > $outputFile
echo "use hive.jm9527_nyu_edu;" >> $outputFile


echo "CREATE TABLE aggregate_sampled_table AS SELECT * FROM gpu0000 TABLESAMPLE BERNOULLI(0.005);" >> $outputFile

for i in $(seq -w 0001 0069)
do
    tableName="gpu$i"
    echo "INSERT INTO aggregate_sampled_table SELECT * FROM $tableName TABLESAMPLE BERNOULLI(0.005);" >> $outputFile
done

# Inform the user
echo "Trino query created in $outputFile"

