For instance_events and collection_events table, the data ingestion method is in repord.md, which is the same as in the ingestion part of the project.

For the instance_usage table, we select every tenth shard of the table so we have a 10% sample that fits on dataproc.
Here's the discp command

`hadoop distcp gs://clusterdata_2019_a/instance_usage-*0.parquet.gz project/data/clusterdata_2019_a/instance_usage_sample0`

All table creation sql are in the sql folder.
