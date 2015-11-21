package lab3;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class PointSet {

	SimplePoint[] points;
	private ArrayList<String> actualClassList = new ArrayList<String>();
	
	
	private PointSet(SimplePoint[] points){ 
		this.points = points; 
	
        // Go through all the points and build the list of all actual classes
        for(int i = 0; i < points.length; i++)
			// Keep a list of all class values.
			if(!actualClassList.contains(points[i].getActualClassValue()))
				actualClassList.add(points[i].getActualClassValue());
	}


	/**
	 * Used to parse a text file for use with KMeans
	 * 
	 * @param filePath - Path to a text file containing the list of points.
	 */
	public static PointSet readPointsFile(File filePath, boolean hasClass){
		
		ArrayList<SimplePoint> pointList = new ArrayList<SimplePoint>();

		
		// Read the file via Scanner.
        try{
            Scanner input = new Scanner(filePath);
            
            // Build the list of points
            while (input.hasNextLine()) {
                String line = input.nextLine();
                pointList.add( new SimplePoint(line, pointList.size()) );
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
		// Convert the points ArrayList to an array of points and return it.
        SimplePoint[] points = new SimplePoint[pointList.size()];
        
        return new PointSet(pointList.toArray(points));
		
	}
	
	/**
	 * Retrieves a point from the PointSet.
	 * 
	 * @param index  	Index of the point to be extracted.
	 * 
	 * @return			A SimplePoint at the specified index.
	 */
	public SimplePoint get(int index){ return this.points[index]; }
	
	public SimplePoint[] getPoints(){ return this.points; }

	/**
	 * Gets the number of elements in the point set.
	 * 
	 * @return Size of the point set.
	 */
	public int size(){ return this.points.length;}
	

	
	public static class SimplePoint implements Comparable<SimplePoint>{
		
		private static final String deliminator = "\t";
		
		private double[] data;
		private int id;
		private String actualClassValue = "XXXXX"; // Can be used to represent the cluster number or class number.
		private String predictedClassValue = "XXXXX"; // Can be used to represent the cluster number or class number.

		
		public SimplePoint(double[] data, int id){
			this.data = data;
			this.id = id;
		}
		
		/**
		 * Constructor for a SimplePoint.  This takes an array of Strings and 
		 * turns it into a KMeans Point.
		 * 
		 * @param data
		 */
		public SimplePoint(String dataLine, int id ){
			
			String[] dataSplit = dataLine.split(deliminator);
			this.data = new double[dataSplit.length - 1]; // Last item is the class value.
			
			for(int i = 0; i < this.data.length; i++){
				try{
					this.data[i] = Double.parseDouble(dataSplit[i]);
				}catch(Exception e){
					System.err.println("Invalid file entry.  Not a double.");
					System.exit(1);
				}
			}
			
			this.id = id;
			
			// Store the class value if applicable.
			actualClassValue = dataSplit[dataSplit.length - 1];
		}
		

		public void setPredictedClassValue(String classValue){ this.predictedClassValue = classValue; }
		public String getActualClassValue(){ return this.actualClassValue; }
		public String getPredictedClassValue(){ return this.predictedClassValue; }
			
		/**
		 * Used to extract the data for a given point.
		 * 
		 * @return Shallow copy of the data for the point
		 */
		public double[] getData(){ return data.clone(); }
		
		/**
		 * Sorts SimplePoints according to their ID number
		 * @param other		A simple
		 * @return
		 */
		@Override
		public int compareTo(SimplePoint other){
			if(this.id < other.id) 			return -1;
			else if(this.id == other.id) 	return 0;
			else							return 1;
		}
		
		/*public String toString(){
			return dataLine + SimplePoint.deliminator + this.classValue;
		}*/
	}
	
	
	
	
	
	public PointSet performHoldout(double holdoutPercentage){
		
		int POINTS_PER_CLASS = 50;
		
		// Get the set of original points
		ArrayList<PointSet.SimplePoint> originalPoints = new ArrayList<PointSet.SimplePoint>(Arrays.asList(this.points));
		// Initialize the set of points to be transferred out
		ArrayList<PointSet.SimplePoint> holdoutPoints = new ArrayList<PointSet.SimplePoint>();
		
		// Delete Points from the back
		for(int i = 3; i > 0; i--){
			// Select the holdout points from the back.
			for(int j = 1; j <= holdoutPercentage * POINTS_PER_CLASS; j++ ){
				PointSet.SimplePoint tempPoint = originalPoints.remove(POINTS_PER_CLASS * i - j);
				holdoutPoints.add(tempPoint);
			}
		}
		// Resize the points array
		this.points = new SimplePoint[originalPoints.size()];
		for(int i = 0; i < points.length; i++)
			this.points[i] = originalPoints.get(i);
		
		// Build the array of holdout points.
		SimplePoint[] holdoutPointArr = new SimplePoint[holdoutPoints.size()];
		for(int i = 0; i < holdoutPointArr.length; i++)
			holdoutPointArr[i] = holdoutPoints.get(i);
		
		// Return the held out points.
		return new PointSet(holdoutPointArr);
	}
	
	
	
	public String[] getAllActualClasses(){
		
		// Sort just so in the expected order.
		Collections.sort(this.actualClassList);
		
		String[] outArr = new String[this.actualClassList.size()];
		actualClassList.toArray(outArr);
		return outArr;
	}
	
	
	
	
	
	
	
	/**
	 * This interface class uses the strategy pattern to determine the distance
	 * between two object of the point class.
	 */
	public interface DistanceMetric{
		public double dist(SimplePoint p1, SimplePoint p2);
	}
	
	
	/**
	 * This class is used as part of the strategy pattern for calculating the 
	 * distance between two objects of type SimplePoint.  Implements the interface
	 * DistanceMetric.  
	 * 
	 * Calculates using Euclidean distance.
	 * 
	 * @author Zayd
	 */
	public static class EuclideanDistance implements DistanceMetric {
	
		public static final String NAME = "euclidean";
		
		public double dist(SimplePoint p1, SimplePoint p2){
			
			// Extract the data from the two points
			double[] p1Data = p1.getData();
			double[] p2Data = p2.getData();
			
			// Ensure the two classes have teh same length
			assert(p1Data.length == p2Data.length);
			
			// Go through each dimension
			double distance = 0;
			for(int i=0; i < p1Data.length; i++)
				distance += Math.pow(p1Data[i] - p2Data[i], 2);
			
			// Take the square root and return.
			return Math.sqrt(distance);
		}
	}
	
	
	/**
	 * This class is used as part of the strategy pattern for calculating the 
	 * distance between two objects of type SimplePoint.  Implements the interface
	 * DistanceMetric.
	 * 
	 * @author Zayd
	 */
	public static class CosineDistance implements DistanceMetric {
		
		public static final String NAME = "cosine";		
		
		public double dist(SimplePoint p1, SimplePoint p2){
			
			// Extract the data from the two points
			double[] p1Data = p1.getData();
			double[] p2Data = p2.getData();
			
			// Ensure the two classes have teh same length
			assert(p1Data.length == p2Data.length);
			
			// Go through each dimension
			double dot = 0, p1Card = 0, p2Card = 0;
			for(int i = 0; i < p1Data.length; i++){
				dot += p1Data[i] * p2Data[i];
				
				p1Card+= Math.pow(p1Data[i], 2);
				p2Card+= Math.pow(p2Data[i], 2);
			}
			
			// Calculate the cosine distance
			double distance = dot / (Math.sqrt(p1Card) * Math.sqrt(p2Card));
			
			// Take the square root and return.
			return 1 - distance;
		}
	}
	
	
	
	public static class Centroid {
		ArrayList<SimplePoint> points = new ArrayList<SimplePoint>();
		double[] centroidCoordinates;
		SimplePoint centroidPoint;
		
		/**
		 * Constructor for the 
		 * @param initialCentroid
		 */
		public Centroid(SimplePoint initialCentroid){
			points.add(initialCentroid);
			this.updateCentroid();
			
		}
		
		
		/**
		 * Used to add a point to a centroid.  This is needed for K-Means clustering.
		 * 
		 * @param point - SimplePoint object 
		 */
		public void addPoint(SimplePoint point){ points.add(point); }

		
		/**
		 * Uses the points assigned to this centroid to update its value.
		 */
		public void updateCentroid(){
			
			// If the centroid is empty, do nothing
			if(points.size() == 0) return;
			
			int numbDimensions = points.get(0).getData().length;
			
			// Initialize the centroid array.
			centroidCoordinates = new double[numbDimensions];
			
			// Calculate the centroid.
			for(int i = 0; i < points.size(); i++){
				double[] data = points.get(i).getData();
				for(int d = 0; d < numbDimensions; d++){
					centroidCoordinates[d] += data[d] / points.size();
				}
			}
			
			centroidPoint = new SimplePoint(centroidCoordinates, -1);
		}
		
//		/**
//		 * Pops and returns a random point from this centroid.
//		 * 
//		 * @return A random point from the centroid.
//		 */
//		public SimplePoint popPoint(){
//			Collections.shuffle(points);
//			return points.remove(0); 
//		}

		
		/**
		 * Removes all points associated with this centroid.
		 */
		public void clearPoints(){
			points.clear();
		}
		
		
		/**
		 * Used to calculate the distance between a point and centroid.
		 * 
		 * @param p1	Simple point whose distance to the centroid will be measured.
		 * @param calc	Object of type distance metric.  Calculates distance between two SimplePoint objects.
		 * 
		 * @return 		Distance between the point and the centroid.
		 */
		public double calculateDistance(SimplePoint p1, DistanceMetric calc){ return calc.dist(p1, centroidPoint); }
		
		
//		/**
//		 * Used to calculate the distance between two centroids.
//		 * 
//		 * @param other	Another centroid.
//		 * @param calc	Object of type distance metric.  Calculates distance between two SimplePoint objects.
//		 * 
//		 * @return 		Distance between the two centroids.
//		 */
//		public double calculateDistance(Centroid other, DistanceMetric calc){ return calc.dist(this.centroidPoint, other.centroidPoint); }
		
		/**
		 * Adds a point to the centroid.
		 * 
		 * @return Number of points assigned to this centroid.
		 */
		public int getNumbPoints(){ return points.size();}
		
		
//		/**
//		 * 
//		 * @param calc	DistanceMetric object for calculating distance.
//		 * @return		Array of the distances.
//		 */
//		public double[] clusterCnt(DistanceMetric calc){
//			
//			double[] distances = new double[ (points.size() * (points.size() -1 ) ) /2 ];
//			int cnt = 0;
//			for(int i = 0; i < points.size(); i++)
//				for(int j = i + 1; j < points.size(); j++)
//					distances[cnt++] += calc.dist(points.get(i), points.get(j));
//			
//			return distances;
//		}
		
		/**
		 * Gets the coordinates of the centroid as a d dimensional array.
		 * 
		 * @return Array of double representing the Centroid's value in d dimensions
		 */
		public PointSet.SimplePoint getCoordinates(){
			double[] returnCoordinates = new double[centroidCoordinates.length];
			
			for(int i = 0; i < centroidCoordinates.length; i++ )
				returnCoordinates[i] = centroidCoordinates[i];
			
			return new PointSet.SimplePoint(returnCoordinates, -1);
			
		}
		
		
		/**
		 * Gets the set of points assigned to this cluster.
		 * 
		 * @return Array of Cluster SimplePoint objects.
		 */
		public PointSet.SimplePoint[] getClusterPoints(){
			SimplePoint[] clusterPoints = new SimplePoint[this.points.size()];
			
			for(int i = 0; i < this.points.size(); i++)
				clusterPoints[i] = this.points.get(i);
			return clusterPoints;
			
		}
		
		/**
		 * Creates a centroid located at the origin (i.e. all 
		 * centroid dimensions are set to 0).
		 * 
		 * @param d Number of dimensions in the centroid
		 * @return
		 */
		public static Centroid createZeroCentroid(int d){
			
			double[] zeroCentroid = new double[d];
			for(int i = 0; i < d; i++)
				zeroCentroid[i] = 0;
			
			return new Centroid(new SimplePoint(zeroCentroid, -1));
		}
		
	}

	
}
