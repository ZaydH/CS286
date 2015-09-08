package mapreducetutorial;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

@SuppressWarnings("unused")
public class WordCount {

	public static class Map extends MapReduceBase
							implements Mapper<LongWritable, Text, Text, IntWritable>{
	
		private final static IntWritable one = new IntWritable(1); // Represents a "1" in the output key value pair.
		private Text word = new Text();
		
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, 
						Reporter reporter) throws IOException{
		
			String line = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(line);
			
			System.out.println("Mapper running.");
			
			while(tokenizer.hasMoreTokens()){
				word.set(tokenizer.nextToken());
				output.collect(word, one); // Write the word and the count 1 to an intermediate file.
			}
			
		}
		
		public static class Reduce extends MapReduceBase
								   implements Reducer<Text, IntWritable, Text, IntWritable>{
		
			public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output,
							   Reporter reporter) throws IOException{
				
				System.out.println("Reducer running.");
				
				int sum = 0;
				while(values.hasNext()){
					sum += values.next().get(); // From the IntWritable, get an int and add it to the sum.
				}
				output.collect(key, new IntWritable(sum)); // Write the sum to a file.
			}
		}
		
		
		/**
		 * Main Driver for the Map Reduce Class.
		 * 
		 * @param args Index 0 is the path to the input file
		 * 			   Index 1 is the path to the output file.
		 */
		public static void main(String[] args)
												throws Exception{
			JobConf conf = new JobConf(WordCount.class);// Define the job configuration.
			
			conf.setJobName("ZaydJob2");
			
			// Define the Output Settings.
			conf.setOutputKeyClass(Text.class);
			conf.setOutputValueClass(IntWritable.class);
			
			// Define the class for the Map and Reduce functionality
			conf.setMapperClass(Map.class);
			conf.setCombinerClass(Reduce.class);	// Repurpose the Reduce class for the Combiner 
			conf.setReducerClass(Reduce.class);
			
			// Note the class names are DIFFERENT
			conf.setInputFormat(TextInputFormat.class);
			conf.setOutputFormat(TextOutputFormat.class);
			
			// Note that method definition changes.
			// Input is file names.
			// Output is a directory.
			FileInputFormat.setInputPaths(conf, new Path(args[0])); // Notice how this is PLURAL
			FileOutputFormat.setOutputPath(conf, new Path(args[1])); // Notice how this is SIGNULAR.
			
			JobClient.runJob(conf); // Run the job.
			
		}
		
		
	}
	
}
