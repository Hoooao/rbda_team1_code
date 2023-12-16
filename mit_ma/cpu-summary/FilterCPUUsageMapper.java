import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FilterCPUUsageMapper
	extends Mapper<LongWritable, Text, Text, Text>{

	private static final int target = 100;	

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException{

		String line = value.toString();
    String[] fields = line.split(",");
    // sanity check 
    if (fields.length != 40)
      return;
    if (line.startsWith("Step")){
      return;
    }
    // 4 = min Epoch Time, 5 = max Epoch Time
    context.write(new Text(fields[4]), value);
	}
}
