import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FilterGPUUsageMapper
	extends Mapper<LongWritable, Text, NullWritable, Text>{

	private static final int target = 100;	

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException{

		String line = value.toString();
    String[] fields = line.split(",");
    // sanity check 
    if (fields.length != 10)
      return;
    String[] outputs = {fields[1], fields[2],fields[3], fields[4],fields[5]};
    String outStr = String.join(",", outputs);
    if (line.startsWith("timestamp")){
      // we do not write header for Hive
      // context.write(NullWritable.get(), new Text(outStr));
      return;
    }
    double gpu_pct = Double.parseDouble(fields[2]);
    double gpu_mem_pct = Double.parseDouble(fields[3]);
    double gpu_mem_used = Double.parseDouble(fields[5]);
    if (gpu_mem_pct > 0 && gpu_mem_used == 0){
      // gpu_mem_pct > 0 but no memory is in use -- anomaly
      return;
    }
    if (gpu_pct == 100){
      context.write(NullWritable.get(), new Text(outStr));
    }
	}
}
