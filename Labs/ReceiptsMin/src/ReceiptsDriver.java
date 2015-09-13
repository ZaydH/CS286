import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

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
		
		// Configure the data input and output file locations
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		// Wait for the job to finish.
		return job.waitForCompletion(true)? 0 : 1;
		
	}
	
	
	public static void main(String[] args) throws Exception{
		
		Configuration conf = new Configuration();
		System.exit(ToolRunner.run(conf, new ReceiptsDriver(), args));
	}
	

}
