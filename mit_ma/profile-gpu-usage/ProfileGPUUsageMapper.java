import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ProfileGPUUsageMapper
	extends Mapper<LongWritable, Text, IntWritable, GPUUsageTuple>{

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException{

		String line = value.toString();
    String[] fields = line.split(",");
    // int index = Integer.parseInt(fields[0]);
    int index = (int) Double.parseDouble(fields[0]);
    double gpu_mem_pct = Double.parseDouble(fields[2]);
    double gpu_mem_free = Double.parseDouble(fields[3]);
    double gpu_mem_used = Double.parseDouble(fields[4]);
    GPUUsageTuple u = new GPUUsageTuple();
    u.setCount(1);
    u.setPct(gpu_mem_pct);
    u.setMemFree(gpu_mem_free);
    u.setMemUsed(gpu_mem_used);
    context.write(new IntWritable(index), u);
	}
}
