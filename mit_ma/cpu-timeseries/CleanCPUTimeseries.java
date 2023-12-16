import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CleanCPUTimeseries{

	public static void main(String[] args) throws Exception{
		if (args.length != 2){
			System.err.println("Usage: CleanCPUTimeseries <input path> <output path>");
			System.exit(-1);
		}

		Job job = Job.getInstance();
		job.setJarByClass(CleanCPUTimeseries.class);
		job.setJobName("CleanCPUTimeseries");

		FileInputFormat.addInputPath(job, new Path(args[0]));
    FileInputFormat.setInputDirRecursive(job, true);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(CleanCPUTimeseriesMapper.class);
		job.setReducerClass(CleanCPUTimeseriesReducer.class);

		job.setNumReduceTasks(1);

		job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(NullWritable.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}


