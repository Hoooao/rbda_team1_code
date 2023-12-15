
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class CleanMSCG {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            // Target type: cpu or mem
            System.err.println("Usage: CleanMSCG <input path> <output path> ");
            System.exit(-1);
        }
        Job job = Job.getInstance();
        job.setJarByClass(CleanMSCG.class);
        job.setJobName("Clean MS Call Graph");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(CleanMSCGMapper.class);
        job.setReducerClass(CleanMSCGReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // 2 reducers
        job.setNumReduceTasks(2);
        System.exit(job.waitForCompletion(true) ? 0 : 1); }
}
