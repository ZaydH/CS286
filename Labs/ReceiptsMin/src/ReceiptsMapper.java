import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class ReceiptsMapper extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable key, Text value, Context context)
					throws IOException, InterruptedException{
		
		// Tokenize the record's value string.
		StringTokenizer strToken = new StringTokenizer(value.toString(), "\\s+");
		
		// Get the year first.
		if(!strToken.hasMoreTokens()) return;
		String year = strToken.nextToken();
		// Skip the next two tokens.
		if(!strToken.hasMoreTokens()) return;
		strToken.nextToken();
		if(!strToken.hasMoreTokens()) return;
		strToken.nextToken();
		// Get the delta
		if(!strToken.hasMoreTokens()) return;
		String delta = strToken.nextToken();
		
		context.write(new Text("dummy"), new Text( year + "_" + delta));
	}
	
}
