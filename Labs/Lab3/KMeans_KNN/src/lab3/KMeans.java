package lab3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.Arrays;

import lab3.PointSet.DistanceMetric;

public class KMeans {

	private static final int INPUT_ARGUMENT_COUNT = 5;
	
	private PointSet allPoints;
	private PointSet.Centroid[] centroid;
	private DistanceMetric calc;
	private File outputFile;
	
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
		
		// Build the dataset
		KMeans kmeans = new KMeans(args[0], args[1], args[2], args[3], args[4] );
		
		// Perform the clustering.
		kmeans.performClustering();
	
		// Exit cleanly.
		System.exit(0);
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
		if(distanceMetric.equals(PointSet.CosineDistance.NAME))
			this.calc = new PointSet.CosineDistance();
		else if(distanceMetric.equals(PointSet.EuclideanDistance.NAME))
			this.calc = new PointSet.EuclideanDistance();
		else{
			System.err.println("Invalid value for the distance metric."); 
			System.exit(1);
		}
		// Get the input file and verify it exists.
		File inputFile = new File(inputFilePath);
		if(!inputFile.exists() || inputFile.isDirectory()){
			System.err.println("Invalid input file."); 
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
		allPoints = PointSet.readPointsFile(inputFile, false);
		
		// Initialize the centroids.
		centroid = new PointSet.Centroid[this.k];
		for(int i = 0; i < this.k; i++)
			centroid[i] = new PointSet.Centroid( allPoints.get(i) );
	}
	
	
	public void performClustering(){
		
		// Go through the maximum number of iterations.
		for(int i = 0; i < maxIterations; i++)
			this.iteration();
		
		// Output the clustering results.
		this.outputResults();
	}
	
	
	
	public void iteration(){
		
		// Get all the points in the point set
		PointSet.SimplePoint[] pointArr = allPoints.getPoints();
		
		// Before starting the next iteration, clear the points in the centroid.
		for(PointSet.Centroid tempCentroid : centroid)
			tempCentroid.clearPoints();
		
		// Iterate through all of the points and determine the distance from the point to each centroid.
		for(PointSet.SimplePoint point : pointArr){
			
			// Determine the closest centroid.
			double bestDistance = centroid[0].calculateDistance(point, this.calc);
			int closestCentroid = 0;
			for(int i = 1; i < this.k; i++){
				double centroidDistance = centroid[i].calculateDistance(point, this.calc);
				if(centroidDistance < bestDistance){
					closestCentroid = i;
					bestDistance = centroidDistance;
				}
			}
			
			point.setClassValue(Integer.toString(closestCentroid + 1));
			// Add the point to the closest centroid.
			centroid[closestCentroid].addPoint(point);
		}
		
		// Verify that all centroids have at least 1 point.
		for(int i = 0; i < this.k; i++){
			if(centroid[i].getNumbPoints() > 0) continue;
			
			// Get a random point from another centroid.
			for(int j = 0; j < this.k; j++){
				// Ensure this cluster is a valid one to get a point from.
				if(i == j || centroid[j].getNumbPoints() < 2)
					continue;
				// Take a point from this centroid.
				centroid[i].addPoint(centroid[j].popPoint());
			}
		}
		
		// Update the centroids
		for(PointSet.Centroid tempCentroid : centroid)
			tempCentroid.updateCentroid();
		
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
			for(int i = 0; i < pointsArr.length; i++){
				fileOut.write( pointsArr[i].toString() );
				// Use this to prevent a blank new line at the end.
				if(i + 1 != pointsArr.length) 
					fileOut.newLine();
			}
			
			
			fileOut.close();
		}
		catch(Exception e){
			System.err.print("Error writing to the output file.");
			System.exit(1);
		}
	}
	
}
