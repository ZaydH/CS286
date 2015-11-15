package lab3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.Arrays;

import lab3.PointSet.DistanceMetric;

public class KNN {

	private static final int INPUT_ARGUMENT_COUNT = 5;
	
	private PointSet trainingSet;
	private PointSet testSet;
	private DistanceMetric calc;
	private File outputFile;
	
	int k = -1;				// Number of Nearest Neighbors
	
	public static void main(String args[]){
		
		/*************************************************************************/
		/*                      Verify the input arguments						 */
		/*************************************************************************/

		// Verify the correct number of input arguments
		if(args.length != INPUT_ARGUMENT_COUNT){
			System.err.println("Invalid number of input arguments."); 
			System.exit(1);
		}
		
		// Build the dataset
		KNN knn = new KNN(args[0], args[1], args[2], args[3], args[4] );
		
		// Perform the clustering.
		kmeans.performClustering();
	
	}
	
	
	public KNN(String k, String testSetFilePath, String distanceMetric, String trainingSetFilePath, String outputFilePath){
		
		
		/*************************************************************************/
		/*                      Verify the input arguments						 */
		/*************************************************************************/
		
		// Verify that k is valid
		try{ this.k = Integer.parseInt(k); }
		catch(Exception e){ System.err.println("Invalid value of k."); System.exit(1);}
		if( this.k <= 0 ){
			System.err.println("Invalid value of k."); 
			System.exit(1);
		}
		// Get the input file and verify it exists.
		File testSetFile = new File(testSetFilePath);
		if(!testSetFile.exists() || testSetFile.isDirectory()){
			System.err.println("Invalid test set file path."); 
			System.exit(1);
		}
		// Verify and parse the distance metric
		if(distanceMetric.equals(PointSet.CosineDistance.NAME))
			this.calc = new PointSet.CosineDistance();
		else if(distanceMetric.equals(PointSet.EuclideanDistance.NAME))
			this.calc = new PointSet.EuclideanDistance();
		else{
			System.err.println("Invalid value for the distance metric."); 
			System.exit(1);
		}
		// Get the input file and verify it exists.
		File trainingSetFile = new File(trainingSetFilePath);
		if(!trainingSetFile.exists() || trainingSetFile.isDirectory()){
			System.err.println("Invalid input file path."); 
			System.exit(1);
		}
		// Get the output file and verify it exists.
		this.outputFile = new File(outputFilePath);
		if(this.outputFile.exists()){
			System.err.println("Invalid output file."); 
			System.exit(1);
		}
		
		/*************************************************************************/
		/*                      Build the KMeans Setup  						 */
		/*************************************************************************/
		
		// Build the point set.
		trainingSet = PointSet.readPointsFile(trainingSetFile);
		

	}
	
	
	
	public void performClassification(){
		
	}
	
	
	public void outputResults(){
		try{
			BufferedWriter fileOut = new BufferedWriter(new FileWriter(outputFile)); // Open the file containing the algorithm comparison results.

			// Print the cluster information.
			fileOut.write("k = " + this.k); fileOut.newLine();
			Class<? extends DistanceMetric> calcClass = this.calc.getClass();
			Field calcNameField = calcClass.getField("NAME");
			fileOut.write("DistanceMetric = " + calcNameField.get(null)); fileOut.newLine();
			
			// Calculate mean intercluster distance
			double totalIntraclusterDistance = 0;
			double numbElements = 0;
			for(int i = 0; i < this.k; i++){
				double[] distances = centroid[i].calculateIntraclusterDistance(this.calc);
				
				// Sum the intracluster distances
				for(int j = 0; j < distances.length; j++ )
					totalIntraclusterDistance += distances[j];
				// Determine the number of elements for intracluster distance.
				numbElements += distances.length;
			}
			fileOut.write("IntraclusterDistance = " + totalIntraclusterDistance/ numbElements); fileOut.newLine();

			
			// Calculate mean intercluster distance
			double totalInterclusterDistance = 0;
			for(int i = 0; i < this.k; i++)
				for(int j = i + 1; j < this.k; j++)
					totalInterclusterDistance += centroid[i].calculateDistance(centroid[j], this.calc);
			double meanIntraclusterDifference = totalInterclusterDistance/ ( this.k * (this.k -1 ) / 2 );
			fileOut.write("InterclusterDistance = " + meanIntraclusterDifference); fileOut.newLine();
			
			// Sort the collection by ID number
			PointSet.SimplePoint[] pointsArr = this.allPoints.getPoints();
			Arrays.sort(pointsArr);
			// Print the point information to a file.
			for(PointSet.SimplePoint point: pointsArr){
				fileOut.write( point.toString() ); fileOut.newLine();
			}
			
			
			fileOut.close();
		}
		catch(Exception e){
			System.err.print("Error writing to the output file.");
			System.exit(1);
		}
	}
	
}
