package lab3;

import java.io.File;

import lab3.PointSet.CosineDistance;
import lab3.PointSet.DistanceMetric;
import lab3.PointSet.EuclideanDistance;

public class KMeans {

	private static final int INPUT_ARGUMENT_COUNT = 5;
	private static final String COSINE_DISTANCE_INPUT_ARG_NAME = "cosine";
	private static final String EUCLIDEAN_DISTANCE_INPUT_ARG_NAME = "cosine";
	
	
	private PointSet allPoints;
	private PointSet.Centroid centroids;
	private DistanceMetric calc;
	
	int k = -1;				// Number of Centroids
	int maxIterations = -1;	// Maximum number of iterations for KMeans
	
	public static void main(String args[]){
		
		/*************************************************************************/
		/*                      Verify the input arguments						 */
		/*************************************************************************/

		// Verify the correct number of input arguments
		if(args.length != INPUT_ARGUMENT_COUNT){
			System.err.println("Invalid number of input arguments."); 
			System.exit(1);
		}

	}
	
	
	public KMeans(String k, String maxIterations, String distanceMetric, String inputFilePath, String outputFilePath){
		
		
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
		// Verify that maxIterations is valid
		try{ this.maxIterations = Integer.parseInt(maxIterations);	}
		catch(Exception e){ System.err.println("Invalid value for the maximum number of iterations."); System.exit(1);}
		if( this.maxIterations <= 0 ){
			System.err.println("Invalid value for the maximum number of iterations."); 
			System.exit(1);
		}
		// Verify and parse the distance metric
		if(distanceMetric.equals(COSINE_DISTANCE_INPUT_ARG_NAME))
			calc = new PointSet.CosineDistance();
		else if(distanceMetric.equals(EUCLIDEAN_DISTANCE_INPUT_ARG_NAME))
			calc = new PointSet.EuclideanDistance();
		else{
			System.err.println("Invalid value for the distance metric."); 
			System.exit(1);
		}
		// Get the input file and verify it exists.
		File inputFile = new File(inputFilePath);
		if(inputFile.exists() || inputFile.isDirectory()){
			System.err.println("Invalid input file."); 
			System.exit(1);
		}
		// Get the output file and verify it exists.
		File outputFile = new File(outputFilePath);
		if(!outputFile.exists() && !outputFile.isDirectory()){
			System.err.println("Invalid output file."); 
			System.exit(1);
		}
		
		/*************************************************************************/
		/*                      Build the KMeans Setup  						 */
		/*************************************************************************/
		
		allPoints.readPointsFile(inputFile);
	}
	
	
	
}
