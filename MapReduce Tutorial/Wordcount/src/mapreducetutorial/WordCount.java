package mapreducetutorial;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class WordCount {

	public static class Map extends MapperBase
							implements Mapper<LongWritable, Text, Text, IntWritable>{
	
		private final static IntWritable one = new IntWritable(1); // Represents a "1" in the output key value pair.
		private Text word = new Text();
		
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, 
						Reporter reporter) throws IOException{
		
		}
		
		
	}
	
}
