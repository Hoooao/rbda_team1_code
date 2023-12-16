## Table Creation

### collection_events
```sql
create external table collection_events_filtered (
    time BIGINT,
    type BIGINT,
    collection_id BIGINT,
    priority BIGINT,
    user VARCHAR,
    collection_name VARCHAR,
    collection_logical_name VARCHAR,
    parent_collection_id BIGINT,
    start_after_collection_ids ARRAY<ROW(element BIGINT)>,
    vertical_scaling BIGINT,
    scheduler BIGINT
)
location '/user/xl3893_nyu_edu/project/data/clusterdata_2019_a/filtered/collection_events/';
```

### instance_usage
```sql
CREATE EXTERNAL TABLE instance_usage (
    start_time BIGINT,
    end_time BIGINT,
    collection_id BIGINT,
    instance_index BIGINT,
    machine_id BIGINT,
    alloc_collection_id BIGINT,
    alloc_instance_index BIGINT,
    collection_type BIGINT,
    average_usage STRUCT<cpus: DOUBLE, memory: DOUBLE>,
    maximum_usage STRUCT<cpus: DOUBLE, memory: DOUBLE>,
    random_sample_usage STRUCT<cpus: DOUBLE, memory: DOUBLE>,
    assigned_memory DOUBLE,
    page_cache_memory DOUBLE,
    cycles_per_instruction DOUBLE,
    memory_accesses_per_instruction DOUBLE,
    sample_rate DOUBLE,
    cpu_usage_distribution ARRAY<DOUBLE>,
    tail_cpu_usage_distribution ARRAY<DOUBLE>
)
STORED AS PARQUET
LOCATION "project/data/clusterdata_2019_a/instance_usage/";
```

```sql
CREATE EXTERNAL TABLE instance_usage_sample (
    start_time BIGINT,
    end_time BIGINT,
    collection_id BIGINT,
    instance_index BIGINT,
    machine_id BIGINT,
    alloc_collection_id BIGINT,
    alloc_instance_index BIGINT,
    collection_type BIGINT,
    average_usage STRUCT<cpus: DOUBLE, memory: DOUBLE>,
    maximum_usage STRUCT<cpus: DOUBLE, memory: DOUBLE>,
    random_sample_usage STRUCT<cpus: DOUBLE, memory: DOUBLE>,
    assigned_memory DOUBLE,
    page_cache_memory DOUBLE,
    cycles_per_instruction DOUBLE,
    memory_accesses_per_instruction DOUBLE,
    sample_rate DOUBLE,
    cpu_usage_distribution ARRAY<DOUBLE>,
    tail_cpu_usage_distribution ARRAY<DOUBLE>
)
STORED AS PARQUET
LOCATION "project/data/clusterdata_2019_a/instance_usage_sample0";
```

### instance_events
```sql
CREATE EXTERNAL TABLE instance_events (
    `time` BIGINT,
    `type` INT,
    collection_id BIGINT,
    scheduling_class BIGINT,
    missing_type TINYINT,
    collection_type TINYINT,
    `priority` INT,
    alloc_collection_id BIGINT,
    instance_index BIGINT,
    machine_id BIGINT,
    alloc_instance_index BIGINT,
    resource_request STRUCT<cpus: DOUBLE, memory: DOUBLE>,
    `constraint` ARRAY<STRUCT<element: STRUCT<name: STRING, value: STRING, relation: BIGINT>>>
)
STORED AS PARQUET
LOCATION "project/data/clusterdata_2019_a/instance_events/";
```

```sql
CREATE EXTERNAL TABLE instance_events_sample (
    `time` BIGINT,
    `type` INT,
    collection_id BIGINT,
    scheduling_class BIGINT,
    missing_type TINYINT,
    collection_type TINYINT,
    `priority` INT,
    alloc_collection_id BIGINT,
    instance_index BIGINT,
    machine_id BIGINT,
    alloc_instance_index BIGINT,
    resource_request STRUCT<cpus: DOUBLE, memory: DOUBLE>,
    `constraint` ARRAY<STRUCT<element: STRUCT<name: STRING, value: STRING, relation: BIGINT>>>
)
STORED AS PARQUET
LOCATION "project/data/clusterdata_2019_a/instance_events_sample/";
```

### machine_events
```sql
CREATE EXTERNAL TABLE machine_events (
    `time` BIGINT,
    machine_id BIGINT,
    `type` TINYINT,
    switch_id STRING,
    capacity STRUCT<cpus: DOUBLE, memory: DOUBLE>,
    platform_id STRING,
    missing_data_reason TINYINT
)
STORED AS PARQUET
LOCATION "project/data/clusterdata_2019_a/machine_events/";
```

## Queries

### Usage vs Requests

```shell
presto --catalog "hive" --schema "instance_index, cpu_usage, cpu_request, unused" --execute "
select usage.instance_index, usage.avgcpu as cpu_usage, event.avgcpu as cpu_request, event.avgcpu-usage.avgcpu as unused from ( select instance_index, avg(average_usage.cpus) as avgcpu from instance_usage_sample Group BY instance_index) as usage join (select instance_index, avg(resource_request.cpus) as avgcpu from instance_events_sample where \"type\"=0 Group By instance_index) as event on usage.instance_index=event.instance_index Order By unused desc;
" --output-format CSV_HEADER > usage-vs-request-sampled.csv
```

### CPU Avg Usage Ranking
```shell
presto --catalog "hive" --schema "instance_index, avgcpu" --execute "select instance_index, avg(average_usage.cpus) as avgcpu from instance_usage Group BY instance_index Order By avgcpu desc;" --output-format CSV_HEADER > cpu-usage-ranking.csv
```

### 7 day Cpu usage
```shell
presto --catalog "hive" --schema "hour_offset, total_cpu_usage" --execute "SELECT 
    FLOOR((start_time - 6000000) / 3600000000) AS hour_offset,
    SUM(average_usage.cpus) AS total_cpu_usage
FROM 
    instance_usage_sample
WHERE 
    start_time >= 6000000
    AND start_time < (6000000 + 24 * 7* 3600000000)
    AND (start_time-6000000)% 3600000000=0
GROUP BY 
    FLOOR((start_time - 6000000) / 3600000000)
ORDER BY 
    FLOOR((start_time - 6000000) / 3600000000);" --output-format CSV_HEADER > 7-day-usage.csv
```

### 7 day CPU Requests
```shell
presto --catalog "hive" --schema "hour_offset, total_cpu_request, total_mem_request" --execute "
SELECT 
    CEILING((time - 6000000) / 3600000000) AS hour_offset,
    SUM(resource_request.cpus) AS total_cpu_request,
    SUM(resource_request.memory) AS total_mem_request
FROM 
    instance_events_sample
WHERE 
    "type" = 0
    AND time >= 6000000
    AND time < (6000000 + 24 * 7* 3600000000)
    AND missing_type is null
    AND resource_request is not null
    AND (time-6000000)% 3600000000>=3595000000
GROUP BY 
    CEILING((time - 6000000) / 3600000000)
ORDER BY 
    CEILING((time - 6000000) / 3600000000);
" --output-format CSV_HEADER > 7-day-resource-requests.csv
```

### Event Types by Priority
```shell
presto --catalog "hive" --schema "priority_level, type, type_count" --execute "
SELECT
    CASE
        WHEN "priority" <= 99 THEN 'Free Tier'
        WHEN "priority" BETWEEN 100 AND 115 THEN 'Basic Tier'
        WHEN "priority" BETWEEN 116 AND 119 THEN 'Mid-Tier'
        WHEN "priority" BETWEEN 120 AND 359 THEN 'Production Tier'
        WHEN "priority" >= 360 THEN 'Monitoring Tier'
        ELSE 'Other'
    END AS priority_level,
    "type",
    COUNT("type") AS type_count
FROM
    instance_events_filtered
GROUP BY
    CASE
        WHEN "priority" <= 99 THEN 'Free Tier'
        WHEN "priority" BETWEEN 100 AND 115 THEN 'Basic Tier'
        WHEN "priority" BETWEEN 116 AND 119 THEN 'Mid-Tier'
        WHEN "priority" BETWEEN 120 AND 359 THEN 'Production Tier'
        WHEN "priority" >= 360 THEN 'Monitoring Tier'
        ELSE 'Other'
    END,
    "type"
ORDER BY
    priority_level,
    type_count DESC;
" --output-format CSV_HEADER > event_type.csv
```

```shell
presto --catalog "hive" --schema "type, type_count" --execute "select \"type\", count(\"type\") as type_count from instance_events_filtered where \"priority\"<360 and \"priority\">=120 group by type order by type_count desc;" --output-format CSV_HEADER > prod_event_type.csv
```

