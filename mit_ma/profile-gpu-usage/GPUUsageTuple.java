import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;
import org.apache.hadoop.io.Writable;

public class GPUUsageTuple implements Writable{

	private double pct = 0;
	private double memFree = 0;
	private double memUsed = 0;
  private long count = 0;

	public void setCount(long v){
		this.count = v;
	}

	public long getCount(){
    return this.count;
	}

	public void setPct(double pct){
		this.pct = pct;
	}

	public double getPct(){
    return this.pct;
	}

	public void setMemFree(double v){
		this.memFree = v;
	}

	public double getMemFree(){
    return this.memFree;
	}

	public void setMemUsed(double v){
    this.memUsed = v;
	}

	public double getMemUsed(){
    return this.memUsed;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
    this.count = in.readLong();
		this.pct = in.readDouble();
		this.memFree = in.readDouble();
		this.memUsed = in.readDouble();
	}

	@Override
	public void write(DataOutput out) throws IOException {
    out.writeLong(this.count);
		out.writeDouble(this.pct);
		out.writeDouble(this.memFree);
		out.writeDouble(this.memUsed);
	}

	@Override
	public String toString(){
		return "pct:" + this.pct + "\tmemFree:" + this.memFree + "\tmemUsed:" + this.memUsed + "\tcount:" + this.count;
	}

}
