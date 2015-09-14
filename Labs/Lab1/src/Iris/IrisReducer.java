package Iris;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.FloatWritable;
import java.io.IOException;


public class IrisReducer  extends Reducer <Text,Text,Text,Text> {
   String[] tempString;
   float tempSepalLength, tempSepalWidth, tempPetalLength, tempPetalWidth;
   float totalSepalLength, totalSepalWidth, totalPetalLength,  totalPetalWidth;
   float minSepalLength, maxSepalLength, meanSepalLength, minSepalWidth, maxSepalWidth, meanSepalWidth, minPetalLength, maxPetalLength, meanPetalLength, minPetalWidth, maxPetalWidth, meanPetalWidth;

   public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

       minSepalLength = minPetalLength = minSepalWidth = minPetalWidth = Float.MAX_VALUE;
       maxSepalLength = maxPetalLength = maxSepalWidth = maxPetalWidth = Float.MIN_VALUE;
    
       int count = 0;

      for(Text value: values) {
         // TODO use String split() method to split value and assign to tempString

         // TODO convert tempString elements to temp sepal/petal length/width vars

         // TODO determine if you have min/max sepal/petal length/widths and assign to min/max sepal/petal lenght/widths accordingly

         // TODO calculate running totals for sepal/petal length/widths for use in calculation of means

         // TODO increment counter for use in calculation of means

      } 
     
      // TODO calculate mean sepal/petal length/width 

      // TODO generate string output per the requirement
      // minSepalLength\tmaxSepalLength\tmeanSepalLength\t ...
    

      // TODO emit output to context
      context.write(key, new Text(output));

   }
}
