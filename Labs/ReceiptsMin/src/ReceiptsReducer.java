import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReceiptsReducer extends Reducer<Text, Text, Text, FloatWritable>{

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException{
		
		int minYear = Integer.MAX_VALUE;
		float minDelta = Float.MAX_VALUE;
		
		// Iterate through all the values specified by the iterator.
		String[] splitString;
		float parsedDelta;
		for(Text value: values){
			// Check if the minimum value is min.
			splitString = value.toString().split("_");
			parsedDelta = Float.parseFloat(splitString[1]);
			if(parsedDelta < minDelta){
				minDelta = parsedDelta;
				minYear = Integer.parseInt(splitString[0]);
			}
		}
		Text outputKeyText = new Text( "min(" + Integer.toString(minYear) + ");" );
		context.write(outputKeyText, new FloatWritable(minDelta));
	}
	
}
