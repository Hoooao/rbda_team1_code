import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FilterCPUUsage{

	public static void main(String[] args) throws Exception{
		if (args.length != 2){
			System.err.println("Usage: FilterCPUUsage <input path> <output path>");
			System.exit(-1);
		}

		Job job = Job.getInstance();
		job.setJarByClass(FilterCPUUsage.class);
		job.setJobName("Filter CPU usage");
		FileInputFormat.addInputPath(job, new Path(args[0]));
    // FileInputFormat.setInputDirRecursive(job, true);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(FilterCPUUsageMapper.class);
		job.setReducerClass(FilterCPUUsageReducer.class);

		job.setNumReduceTasks(1);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}


