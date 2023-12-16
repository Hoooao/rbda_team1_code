import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.parquet.avro.AvroReadSupport;
import org.apache.parquet.hadoop.ParquetInputFormat;

import java.io.IOException;
import java.util.*;

public class DataFilter {
    enum Counter {
        TOTAL, VALID, MISSING_TYPE, OUT_OF_RANGE
    }



    public static class InstanceEventsFilterMapper extends Mapper<Void, GenericRecord, NullWritable, Text> {
        @Override
        protected void map(Void key, GenericRecord value, Context context) throws IOException, InterruptedException {
            long time = (long) value.get("time");
            Long missing_type = (Long) value.get("missing_type");

            if (missing_type == null && time != 0) {// Write valid records to the output
                GenericRecord outputRecord = getInstanceEventsOutputRecord(value);
                context.write(NullWritable.get(), new Text(outputRecord.toString()));
                context.getCounter(Counter.VALID).increment(1);
            } else if (missing_type != null) {
                context.getCounter(Counter.MISSING_TYPE).increment(1);
            }
            if (time == 0) {
                context.getCounter(Counter.OUT_OF_RANGE).increment(1);
            }

            context.getCounter(Counter.TOTAL).increment(1);

        }

        private static final Schema instanceEventsOutputSchema = new Schema.Parser().parse("{\n" +
                "  \"type\": \"record\",\n" +
                "  \"name\": \"InstanceEventsOutputSchema\",\n" +
                "  \"fields\": [\n" +
                "    {\"name\": \"time\", \"type\": \"long\"},\n" +
                "    {\"name\": \"type\", \"type\": \"long\"},\n" +
                "    {\"name\": \"collection_id\", \"type\": \"long\"},\n" +
                "    {\"name\": \"priority\", \"type\": \"long\"},\n" +
                "    {\"name\": \"instance_index\", \"type\": \"long\"},\n" +
                "    {\"name\": \"machine_id\", \"type\": [\"null\", \"long\"]},\n" +
                "    {\n" +
                "      \"name\": \"resource_request\",\n" +
                "      \"type\": [\"null\", {\n" +
                "        \"type\": \"record\",\n" +
                "        \"name\": \"ResourceRequest\",\n" +
                "        \"fields\": [\n" +
                "          {\"name\": \"cpus\", \"type\": [\"null\", \"double\"]},\n" +
                "          {\"name\": \"memory\", \"type\": [\"null\", \"double\"]}\n" +
                "        ]\n" +
                "      }]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"constraint\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"array\",\n" +
                "        \"items\": {\n" +
                "          \"type\": \"record\",\n" +
                "          \"name\": \"Constraint\",\n" +
                "          \"fields\": [\n" +
                "            {\"name\": \"name\", \"type\": [\"null\", \"string\"]},\n" +
                "            {\"name\": \"value\", \"type\": [\"null\", \"string\"]},\n" +
                "            {\"name\": \"relation\", \"type\": [\"null\", \"long\"]}\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}\n");

        private GenericRecord getInstanceEventsOutputRecord(GenericRecord record){
            GenericRecord outputRecord = new GenericData.Record(instanceEventsOutputSchema);
            outputRecord.put("time", record.get("time"));
            outputRecord.put("type", record.get("type"));
            outputRecord.put("collection_id", record.get("collection_id"));
            outputRecord.put("priority", record.get("priority"));
            outputRecord.put("instance_index", record.get("instance_index"));
            outputRecord.put("machine_id", record.get("machine_id"));
            outputRecord.put("resource_request", record.get("resource_request"));
            outputRecord.put("constraint", record.get("constraint"));
            return outputRecord;
        }
    }

    public static class CollectionEventsFilterMapper extends Mapper<Void, GenericRecord, NullWritable, Text> {
        @Override
        protected void map(Void key, GenericRecord value, Context context) throws IOException, InterruptedException {
            long time = (long) value.get("time");
            Long missing_type = (Long) value.get("missing_type");

            if (missing_type == null && time != 0) {// Write valid records to the output
                GenericRecord outputRecord = getCollectionEventsOutputRecord(value);
                context.write(NullWritable.get(), new Text(outputRecord.toString()));
                context.getCounter(Counter.VALID).increment(1);
            } else if (missing_type != null) {
                context.getCounter(Counter.MISSING_TYPE).increment(1);
            }
            if (time == 0) {
                context.getCounter(Counter.OUT_OF_RANGE).increment(1);
            }

            context.getCounter(Counter.TOTAL).increment(1);

        }

        private static final Schema collectionEventsOutputSchema = new Schema.Parser().parse("{\n" +
                "  \"type\": \"record\",\n" +
                "  \"name\": \"CollectionEventsOutputSchema\",\n" +
                "  \"fields\": [\n" +
                "    {\"name\": \"time\", \"type\": [\"null\", \"long\"]},\n" +
                "    {\"name\": \"type\", \"type\": [\"null\", \"long\"]},\n" +
                "    {\"name\": \"collection_id\", \"type\": [\"null\", \"long\"]},\n" +
                "    {\"name\": \"priority\", \"type\": [\"null\", \"long\"]},\n" +
                "    {\"name\": \"user\", \"type\": [\"null\", \"string\"]},\n" +
                "    {\"name\": \"collection_name\", \"type\": [\"null\", \"string\"]},\n" +
                "    {\"name\": \"collection_logical_name\", \"type\": [\"null\", \"string\"]},\n" +
                "    {\"name\": \"parent_collection_id\", \"type\": [\"null\", \"long\"]},\n" +
                "    {\n" +
                "      \"name\": \"start_after_collection_ids\",\n" +
                "      \"type\": {\n" +
                "        \"type\": \"array\",\n" +
                "        \"items\": {\n" +
                "          \"type\": \"record\",\n" +
                "          \"name\": \"StartAfterCollectionIds\",\n" +
                "          \"fields\": [\n" +
                "            {\"name\": \"element\", \"type\": \"long\"}\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    {\"name\": \"vertical_scaling\", \"type\": [\"null\", \"long\"]},\n" +
                "    {\"name\": \"scheduler\", \"type\": [\"null\", \"long\"]}\n" +
                "  ]\n" +
                "}\n");

        private GenericRecord getCollectionEventsOutputRecord(GenericRecord record) {
            GenericRecord outputRecord = new GenericData.Record(collectionEventsOutputSchema);
            outputRecord.put("time", record.get("time"));
            outputRecord.put("type", record.get("type"));
            outputRecord.put("collection_id", record.get("collection_id"));
            outputRecord.put("priority", record.get("priority"));
            outputRecord.put("user", record.get("user"));
            outputRecord.put("collection_name", record.get("collection_name"));
            outputRecord.put("collection_logical_name", record.get("collection_logical_name"));
            outputRecord.put("parent_collection_id", record.get("parent_collection_id"));
            outputRecord.put("start_after_collection_ids", record.get("start_after_collection_ids"));
            outputRecord.put("vertical_scaling", record.get("vertical_scaling"));
            outputRecord.put("scheduler", record.get("scheduler"));
            return outputRecord;
        }
    }

    private static final Map<String,Class> schemaMap = new HashMap<String,Class>();
    static {
        schemaMap.put("InstanceEvents", InstanceEventsFilterMapper.class);
        schemaMap.put("CollectionEvents", CollectionEventsFilterMapper.class);
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 3){
            System.err.println("Usage: DataFilter <input path> <output path> <table>");
            System.exit(-1);
        }
        String inputPath = args[0];
        String outPath = args[1];
        String Table = args[2];

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, Table+"FilterJob");
        job.setJarByClass(DataFilter.class);
        job.setNumReduceTasks(0);

        // Mapper Configuration
        job.setMapperClass(schemaMap.get(Table));

        // Input Configuration
        ParquetInputFormat.addInputPath(job, new Path(inputPath));
        ParquetInputFormat.setReadSupportClass(job, AvroReadSupport.class);
        job.setInputFormatClass(ParquetInputFormat.class);

        // Output Configuration
        FileOutputFormat.setOutputPath(job, new Path(outPath));
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        // Execute job and wait for completion
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}