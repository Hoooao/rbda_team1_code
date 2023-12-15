
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class CleanMSCGReducer extends Reducer<Text, Text, Text, NullWritable> {

    @Override   
    public void setup(Context context) throws IOException, InterruptedException{     
        context.write(new Text("traceid,um,uminstanceid,dm,dminstanceid"),NullWritable.get());
    }

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            context.write(value, NullWritable.get());
        }
    }
}

