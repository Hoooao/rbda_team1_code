import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ProfileGPUUsage{

	public static void main(String[] args) throws Exception{
		if (args.length != 2){
			System.err.println("Usage: ProfileGPUUsage <input path> <output path>");
			System.exit(-1);
		}

		Job job = Job.getInstance();
		job.setJarByClass(ProfileGPUUsage.class);
		job.setJobName("Profile GPU usage data");
		FileInputFormat.addInputPath(job, new Path(args[0]));
    FileInputFormat.setInputDirRecursive(job, true);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(ProfileGPUUsageMapper.class);
		job.setCombinerClass(ProfileGPUUsageReducer.class);
		job.setReducerClass(ProfileGPUUsageReducer.class);

		job.setNumReduceTasks(1);

		// job.setMapOutputKeyClass(IntWritable.class);
		// job.setMapOutputValueClass(GPUUsageTuple.class);
		// job.setOutputValueClass(Text.class);
    job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(GPUUsageTuple.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}


