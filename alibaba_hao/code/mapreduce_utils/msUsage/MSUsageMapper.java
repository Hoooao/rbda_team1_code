import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MSUsageMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String stat = value.toString();
        String[] statArray = stat.split(",");
        String targetType = context.getConfiguration().get("target.type");
        String usage = "";
        // ommit the csv header
        if (!statArray[0].equals("timestamp")) {
            // preserve 2 decimal as string
            try {
                if (targetType.equals("cpu")) {
                    usage = String.format("%.2f", Double.parseDouble(statArray[4]));
                } else if (targetType.equals("mem")) {
                    usage = String.format("%.2f", Double.parseDouble(statArray[5]));
                }
                context.write(new Text(usage), new IntWritable(1));
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}

