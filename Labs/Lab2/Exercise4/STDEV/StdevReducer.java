package Stdev;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.FloatWritable;
import java.io.IOException;
import java.util.StringTokenizer;


@SuppressWarnings("unused")
public class StdevReducer  extends Reducer <Text,Text,Text,FloatWritable> {
	
	private int itemCount;
	private float mean;
	
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		// Initialize the variable values
		String keyStr = key.toString();

		if(keyStr.equals("mean")){
			itemCount = 0;
			mean = 0;
			
			// Sum up all the elements
			for(Text value: values){
				itemCount++;
				mean += Float.parseFloat(value.toString());
			}
			mean /= itemCount;
		}
		else{

			float stdDev = 0;

			// Add the sum of squared mean differences
			for(Text value: values){
				stdDev += Math.pow(Float.parseFloat(value.toString()) - mean, 2);
			}
			stdDev = (float)Math.pow( stdDev / (itemCount - 1), 0.5);
			
			// TODO emit output to context
			context.write(key, new FloatWritable(stdDev));
		} 




	}
}
