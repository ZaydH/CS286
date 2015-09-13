import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.util.*;

public class ReceiptsDriver extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		
		if(args.length != 2){
			System.err.printf("usage: %s [general options] <inputfile> <outputfile>\n",  getClass().getSimpleName());
			System.exit(1);
		}
		
		// Configure the job
		@SuppressWarnings("deprecation")
		Job job = new Job(getConf(), "receipts_min");

		job.setJarByClass(ReceiptsDriver.class);
		job.setMapperClass(ReceiptsMapper.class);
		job.setReducerClass(ReceiptsReducer.class);
		
		// Define input file's format (e.g. text file)
		job.setInputFormatClass(TextInputFormat.class);
		
		// Setup the mapper output classes.
		// Input class are a LongWritable by default and Text
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		// Set the reducer's output class.
		job.setOutputKeyClass(Text.class);
		job.setOutputKeyClass(FloatWritable.class);
		
		// Wait for the job to finish.
		return job.waitForCompletion(true)? 0 : 1;
		
	}
	
	
	public static void main(String[] args) throws Exception{
		
		Configuration conf = new Configuration();
		System.exit(ToolRunner.run(conf, new ReceiptsDriver(), args));
	}
	

}
