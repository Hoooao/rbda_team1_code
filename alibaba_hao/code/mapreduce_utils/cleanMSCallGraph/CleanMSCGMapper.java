import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CleanMSCGMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // timestamp0, service2, rpc_id,3 rpctype6, interface7,rt10 are removed
        String stat = value.toString();
        String[] statArray = stat.split(",");
        // get the traceid
        String tr_id = statArray[1];
        String res = tr_id;
        // ommit the csv header
        if (!statArray[0].equals("timestamp")) {
            try {
                for (int i = 0; i < statArray.length; i++) {
                    switch (i) {
                        case 6:case 7:case 10:
                            continue;
                        default:
                            res += "," + statArray[i];
                            continue;
                    }
                }
                context.write(new Text(tr_id),new Text(res) );
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}

