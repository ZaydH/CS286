package lab3;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PointSet {

	SimplePoint[] points;
	
	
	private PointSet(SimplePoint[] points){ this.points = points; }

	/**
	 * Used to parse a text file for use with KMeans
	 * 
	 * @param filePath - Path to a text file containing the list of points.
	 */
	public static PointSet readPointsFile(File filePath){
		
		ArrayList<SimplePoint> pointList = new ArrayList<SimplePoint>();

		
		// Read the file via Scanner.
        try{
            Scanner input = new Scanner(filePath);
            
            // Build the list of points
            while (input.hasNextLine()) {
                String line = input.nextLine();
                pointList.add( new SimplePoint(line, pointList.size(), false) );
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
        // Shuffle the points list.
        Collections.shuffle(pointList);
        
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


	
	public static class SimplePoint implements Comparable<SimplePoint>{
		
		private static final String deliminator = "\t";
		
		private double[] data;
		private String dataLine;
		private int id;
		private String classValue = "XXXXX"; // Can be used to represent the cluster number or class number.
		
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
		public SimplePoint(String dataLine, int id, boolean hasClass){
			
			this.dataLine = dataLine;
			String[] data = dataLine.split(deliminator);
			// Handle the case of a class value
			int length;
			if(hasClass)
				length = data.length - 1;
			else
				length = data.length;
			this.data = new double[data.length];
			
			for(int i = 0; i < length; i++){
				try{
					this.data[i] = Double.parseDouble(data[i]);
				}catch(Exception e){
					System.err.println("Invalid file entry.  Not a double.");
					System.exit(1);
				}
			}
			
			this.id = id;
			
			// Store the class value if applicable.
			if(hasClass)
				classValue = data[length];
			else
				classValue = "XXXXXX";
		}
		

		public void setClassNumber(String classValue){ this.classValue = classValue; }
		public String getClassValue(){ return this.classValue; }
			
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
		
		
		
		public String toString(){
			return dataLine + SimplePoint.deliminator + this.classValue;
		}
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
		double[] centroid;
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
			
			int numbDimensions = points.get(0).getData().length;
			
			// Initialize the centroid array.
			centroid = new double[numbDimensions];
			
			// Calculate the centroid.
			for(int i = 0; i < points.size(); i++){
				double[] data = points.get(i).getData();
				for(int d = 0; d < numbDimensions; d++){
					centroid[d] += data[d] / points.size();
				}
			}
			
			centroidPoint = new SimplePoint(centroid, -1);
		}
		
		/**
		 * Pops and returns a random point from this centroid.
		 * 
		 * @return A random point from the centroid.
		 */
		public SimplePoint popPoint(){
			Collections.shuffle(points);
			return points.remove(0); 
		}

		
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
		
		
		/**
		 * Used to calculate the distance between two centroids.
		 * 
		 * @param other	Another centroid.
		 * @param calc	Object of type distance metric.  Calculates distance between two SimplePoint objects.
		 * 
		 * @return 		Distance between the two centroids.
		 */
		public double calculateDistance(Centroid other, DistanceMetric calc){ return calc.dist(this.centroidPoint, other.centroidPoint); }
		
		/**
		 * Adds a point to the centroid.
		 * 
		 * @return Number of points assigned to this centroid.
		 */
		public int getNumbPoints(){ return points.size();}
		
		
		/**
		 * 
		 * @param calc	DistanceMetric object for calculating distance.
		 * @return		Array of the distances.
		 */
		public double[] calculateIntraclusterDistance(DistanceMetric calc){
			
			double[] distances = new double[ (points.size() * (points.size() -1 ) ) /2 ];
			int cnt = 0;
			for(int i = 0; i < points.size(); i++)
				for(int j = i + 1; j < points.size(); j++)
					distances[cnt++] += calc.dist(points.get(i), points.get(j));
			
			return distances;
		}
		
		
	}

	
}
