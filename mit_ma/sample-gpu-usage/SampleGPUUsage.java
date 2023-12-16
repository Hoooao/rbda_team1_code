import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class SampleGPUUsage{

	public static void main(String[] args) throws Exception{
		if (args.length != 3) {
				System.err.println("Usage: SampleGPUUsage <input path> <output path> <filter percentage>");
				System.exit(-1);
		}

		Configuration conf = new Configuration();
		// Set the 'filter_percentage' configuration parameter
		conf.set("filter_percentage", args[2]);
    System.out.println(args[2]+ conf.get("filter_percentage"));

		Job job = Job.getInstance(conf, "Sample GPU usage");
		job.setJarByClass(SampleGPUUsage.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
    // FileInputFormat.setInputDirRecursive(job, true);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(SampleGPUUsageMapper.class);

		job.setNumReduceTasks(0);

		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}


