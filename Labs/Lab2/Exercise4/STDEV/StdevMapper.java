package Stdev;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;

import java.io.IOException;
import java.util.StringTokenizer;

public class StdevMapper extends Mapper <LongWritable,Text,Text,Text> {
	
	private static final boolean USE_STRING_TOKENIZER = false;
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			
		String[] tempString = value.toString().split("\\s+");
			
		// Key for the mean calculation is first alphabetically
		context.write(new Text(tempString[4] + "_mean"), new Text(tempString[2]) );

		// Key for the standard deviation calculation is second alphabetically
		// Value for both is identical since we will calculate the difference twice.
		context.write(new Text(tempString[4] + "_stdev"), new Text(tempString[2]) );

   }
}
