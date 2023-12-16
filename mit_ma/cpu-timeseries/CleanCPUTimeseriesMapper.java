import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CleanCPUTimeseriesMapper
	extends Mapper<LongWritable, Text, Text, NullWritable>{

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException{

		String line = value.toString();
    String[] fields = line.split(",");
    if (fields.length != 13)
      return;
    if (fields[0].equals("Step"))
      return;

    String node = fields[1];

    context.write(new Text(node), NullWritable.get());
	}
}
