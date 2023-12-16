import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class ProfileGPUUsageReducer
	extends Reducer<IntWritable, GPUUsageTuple, IntWritable, GPUUsageTuple>{

	@Override
	public void reduce(IntWritable key, Iterable<GPUUsageTuple> values, Context context)
		throws IOException, InterruptedException{

    double pctSum = 0;
    double memFreeSum = 0;
    double memUsedSum = 0;
    long count = 0;
    //double curPct = 0;
    //double curMemFree = 0;
    //double curMemUsed = 0;

    for(GPUUsageTuple val: values){
      long newCount = val.getCount();
      if (newCount == 0)
        continue;
      //curPct = count / (count + newCount) * curPct + newCount / (count + newCount) * val.getPct();
      //curMemFree = count / (count + newCount) * curMemFree + newCount / (count + newCount) * val.getMemFree();
      //curMemUsed = count / (count + newCount) * curMemUsed + newCount / (count + newCount) * val.getMemUsed();
      //count += newCount;
      count += val.getCount();
      pctSum += val.getPct() * newCount;
      memFreeSum += val.getMemFree() * newCount;
      memUsedSum += val.getMemUsed() * newCount;
    }

    GPUUsageTuple u = new GPUUsageTuple();
    u.setCount(count);
    u.setPct(pctSum / count);
    u.setMemFree(memFreeSum / count);
    u.setMemUsed(memUsedSum / count);
    context.write(key, u);
	}
}
