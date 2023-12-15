
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class NodeUsage {

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            // Target type: cpu or mem
            System.err.println("Usage: NodeUsage <input path> <output path> <target type>");
            System.exit(-1);
        }
        Job job = Job.getInstance();
        job.setJarByClass(NodeUsage.class);
        job.setJobName("Node CPU&Mem Usage");
        job.getConfiguration().set("target.type", args[2]);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(CleanMSCGMapper.class);
        job.setReducerClass(MSUsageReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // Combiner added, same as reducer
        job.setCombinerClass(MSUsageReducer.class);
        // Only one reducer needed
        job.setNumReduceTasks(1);
        System.exit(job.waitForCompletion(true) ? 0 : 1); }
}
