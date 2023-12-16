import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.Random;

public class SampleGPUUsageMapper
	extends Mapper<LongWritable, Text, NullWritable, Text>{

	private Random rands = new Random();
  private Double percentage;
  //private Double percentage = 0.5 / 100.0;

	@Override
  public void setup(Context context) throws IOException, InterruptedException {
    // Retrieve the percentage that is passed in via the configuration
    //   like this: conf.set("filter_percentage", .5);
    //     for .5%
    String strPercentage = context.getConfiguration().get("filter_percentage");
    System.out.println(strPercentage);
    percentage = Double.parseDouble(strPercentage) / 100.0;
  }

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException{

		String line = value.toString();
    String[] fields = line.split(",");
    // sanity check 
    if (fields.length != 10)
      return;
    if (line.startsWith("timestamp")){
      // we do not write header for Hive
      return;
    }
    if (rands.nextDouble() < percentage){
      context.write(NullWritable.get(), value);
    }
	}
}
