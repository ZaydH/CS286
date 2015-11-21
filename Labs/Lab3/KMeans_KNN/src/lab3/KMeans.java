package lab3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Random;

import lab3.PointSet.Centroid;
import lab3.PointSet.DistanceMetric;

public class KMeans {

	private static final int INPUT_ARGUMENT_COUNT = 7;
	
	private PointSet allPoints;
	
	private PointSet.Centroid[] centroid;
	private String centroidInit;
	private DistanceMetric calc;
	private File outputFile;
	private double delta = -1;
	private int numbDimensions = 4;
	
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
		KMeans kmeans = new KMeans(args[0], args[1], args[2], args[3], args[4], args[5], args[6] );
		
		// Perform the clustering.
		kmeans.performClustering();
	
		// Exit cleanly.
		System.exit(0);
	}
	
	
	public KMeans(String k, String maxIterations, String delta, 
				  String distanceMetric, String init, String inputFilePath, String outputFilePath){
		
		
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
		// Verify that delta is valid
		try{ this.delta = Double.parseDouble(delta);	}
		catch(Exception e){ System.err.println("Invalid value for delta."); System.exit(1);}
		if( this.delta <= 0 ){
			System.err.println("Invalid value for delta."); 
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
		// Verify that the initialize of the centroids is valid.
		this.centroidInit = init;
		if(!this.centroidInit.equals("random") && !this.centroidInit.equals("zero")
		   && !this.centroidInit.equals("partition") ){
			System.err.println("Invalid centroid initialization value."); 
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
		initializeCentroids();
	}
	
	
	
	
	private void initializeCentroids(){
		
		
		
		// Initialize the centroid data structure.
		centroid = new PointSet.Centroid[this.k];
		
		// Initialize the centroid to random points.
		if(this.centroidInit.equals("random")){
			int numbPoints = allPoints.size();
			Random rand = new Random();
			
			// Initialize the centroids to random points.
			for(int i = 0; i < k; i++)
				centroid[i] = new Centroid( allPoints.get(rand.nextInt(numbPoints)) );
			return;
			
		}
		// Initialize all centroids to zero.
		else if(this.centroidInit.equals("zero")){
			for(int i = 0; i < this.k; i++)
				this.centroid[i] = PointSet.Centroid.createZeroCentroid(numbDimensions);
			return;
		}
		else if(this.centroidInit.equals("partition")){
			// Create the arrays to store the partition values.
			double[] dimMin = new double[this.numbDimensions];
			double[] dimMax = new double[this.numbDimensions];
			for(int d = 0; d < this.numbDimensions; d++){
				dimMin[d] = Double.MAX_VALUE;
				dimMax[d] = Double.MIN_VALUE;
			}
			
			// Go through all the points and determine the min and max
			for(int p = 0; p < allPoints.size(); p++){
				double[] pointLoc  = allPoints.get(p).getData();
				for(int d = 0; d < this.numbDimensions; d++){
					dimMin[d] = Math.min(dimMin[d], pointLoc[d]);
					dimMax[d] = Math.max(dimMax[d], pointLoc[d]);
				}
			}
			
			// Go through the number centroids and define the centroids.
			for(int i = 0; i < k; i++){
				double[] centroidLoc = new double[this.numbDimensions];
				for(int d = 0; d < this.numbDimensions; d++){
					double partitionWidth = (dimMax[d] - dimMin[d])/(k);
					
					centroidLoc[d] = dimMin[d] + partitionWidth * (i + 0.5);
				}
				centroid[i] = new PointSet.Centroid( new PointSet.SimplePoint(centroidLoc, -1) );
			}
			return;
		}
		// Invalid value of the centroid initialization
		else
			assert(false);
	}
	
	public void performClustering(){
		
		// Go through the maximum number of iterations.
		int itrCnt = 0;
		double maxChange;
		do{
			
			// Store the previous centroid locations
			PointSet.SimplePoint prevCentroidCoordinate[] = new PointSet.SimplePoint[k];
			for(int j = 0; j < k; j++)
				prevCentroidCoordinate[j] = centroid[j].getCoordinates();
			
			this.iteration();
			
			// Calculate the delta change
			maxChange = Double.MIN_VALUE;
			for(int j = 0; j < k; j++)
				maxChange = Math.max(maxChange, calc.dist( prevCentroidCoordinate[j], centroid[j].getCoordinates() ) );
			// Increment the iteration counter
			itrCnt++;
		} while(itrCnt < maxIterations && maxChange > this.delta);
		
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
			
			point.setPredictedClassValue(Integer.toString(closestCentroid + 1));
			// Add the point to the closest centroid.
			centroid[closestCentroid].addPoint(point);
		}
		
		/*// Verify that all centroids have at least 1 point.
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
		}*/
		
		// Update the centroids
		for(PointSet.Centroid tempCentroid : centroid)
			tempCentroid.updateCentroid();
		
	}
	
	
	
	public double[] calculateClusterAccuracy(){
		
		// Hashtable is used to track the class distribution.
		Hashtable<String, int[]> classDistribution = new Hashtable<String, int[]>();
		
		// Go through all the clusters and determine
		for(int i = 0; i < k; i++){
			
		}
		
		return null;
	}
	
	
	
	public double calculateMeanInterclusterDistance(){
		
		// Calculate mean intercluster distance
		double totalInterclusterDistance = 0;
		int numbElements = 0;
		PointSet.SimplePoint outerCentroid, innerCentroid;
		for(int i = 0; i < this.k; i++){
			outerCentroid = this.centroid[i].getCoordinates();
			for(int j = i + 1; j < this.k; j++){
				innerCentroid = this.centroid[j].getCoordinates();
				totalInterclusterDistance += this.calc.dist(outerCentroid, innerCentroid);
			}
		}
		assert(numbElements == this.k * (this.k -1 ) / 2 );
		double meanIntraclusterDifference = totalInterclusterDistance / ( this.k * (this.k -1 ) / 2 );

		return meanIntraclusterDifference;
	}
	
	
	public double calculateMeanIntraclusterDistance(){
		
		// Calculate mean intercluster distance
		double totalIntraclusterDistance = 0;
		for(int clusterCnt = 0; clusterCnt < this.k; clusterCnt++){
			
			// Get the centroid's location.
			PointSet.SimplePoint centroidPoint = centroid[clusterCnt].getCoordinates();
			PointSet.SimplePoint[] clusterPoints = centroid[clusterCnt].getClusterPoints();
			
			// Sum the intracluster distances
			for(int j = 0; j < clusterPoints.length; j++ )
				totalIntraclusterDistance += this.calc.dist(centroidPoint, clusterPoints[j]);
		}
		
		return totalIntraclusterDistance / this.allPoints.size() ;
	}
	
	
	public void outputResults(){
		
		try{
			BufferedWriter fileOut = new BufferedWriter(new FileWriter(outputFile)); // Open the file containing the algorithm comparison results.

			// Print the cluster information.
			fileOut.write("k = " + this.k); fileOut.newLine();
			Class<? extends DistanceMetric> calcClass = this.calc.getClass();
			Field calcNameField = calcClass.getField("NAME");
			fileOut.write("distance = " + calcNameField.get(null));
			
			// Print the centroid information.
			for(int i = 0; i < this.k; i++){
				fileOut.write("centroid " + i + " = [");
				
				PointSet.SimplePoint centroidPoint = centroid[i].getCoordinates();
				
				// Print the centroid point for each dimension
				for(int j = 0; j < centroidPoint.getData().length; j++){
					// Add a comma separator between the points
					if(j != 0) fileOut.write(", ");
					fileOut.write(Double.toString(centroidPoint.getData()[j]));
				}
				
				fileOut.newLine();
				fileOut.write("]");
			}
			
			// Output the calculated intra- and inter-cluster distances
			fileOut.newLine();
			fileOut.write("mean intercluster distance = " + calculateMeanInterclusterDistance() );
			fileOut.newLine();
			fileOut.write("mean intracluster distance = " + calculateMeanIntraclusterDistance() ); 
			
//			// Print the cluster accuracy.
//			double[] clusterAccuracy = calculateClusterAccuracy();
//			for(int i = 0; i < clusterAccuracy.length; i++){
//				fileOut.newLine();
//				fileOut.write("measure of species " +  (i + 1)  + " in same cluster= " + clusterAccuracy[i] );
//			}
			
			/*// Sort the collection by ID number
			PointSet.SimplePoint[] pointsArr = this.allPoints.getPoints();
			Arrays.sort(pointsArr);
			// Print the point information to a file.
			for(int i = 0; i < pointsArr.length; i++){
				fileOut.write( pointsArr[i].toString() );
				// Use this to prevent a blank new line at the end.
				if(i + 1 != pointsArr.length) 
					fileOut.newLine();
			}*/
			
			
			fileOut.close();
		}
		catch(Exception e){
			System.err.print("Error writing to the output file.");
			System.exit(1);
		}
	}
	
}
