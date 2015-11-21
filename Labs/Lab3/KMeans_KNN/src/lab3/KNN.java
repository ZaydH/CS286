package lab3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

import lab3.PointSet.DistanceMetric;

public class KNN {

	private static final int INPUT_ARGUMENT_COUNT = 5;
	
	private PointSet trainingSet;
	private PointSet testSet;
	private DistanceMetric calc;
	private File outputFile;
	
	private Hashtable<String, Integer> accuracyTable = new Hashtable<String, Integer>();
	
	int k = -1;				// Number of Nearest Neighbors
	double holdoutPercentage;
	
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
		knn.performClassification();
	
	}
	
	
	public KNN(String holdoutPercentage, String k, String distanceMetric, String trainingSetFilePath, String outputFilePath){
		
		
		/*************************************************************************/
		/*                      Verify the input arguments						 */
		/*************************************************************************/
		
		// Verify that the holdout percentage is valid
		try{ this.holdoutPercentage = Double.parseDouble(holdoutPercentage); }
		catch(Exception e){ System.err.println("Invalid value of holdout percentage."); System.exit(1);}
		if( this.holdoutPercentage < 0.05 || this.holdoutPercentage > 0.2 ){
			System.err.println("Invalid value of holdout percentage."); 
			System.exit(1);
		}
		// Verify that k is valid
		try{ this.k = Integer.parseInt(k); }
		catch(Exception e){ System.err.println("Invalid value of k."); System.exit(1);}
		if( this.k <= 0 ){
			System.err.println("Invalid value of k."); 
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
			System.err.println("Output file already exists."); 
			System.exit(1);
		}
		
		/*************************************************************************/
		/*                      Build the KMeans Setup  						 */
		/*************************************************************************/
		
		// Build the point set.
		trainingSet = PointSet.readPointsFile(trainingSetFile, true);
		testSet = trainingSet.performHoldout(this.holdoutPercentage);
		
		// Verify that k is given the training set size.
		if(this.k > trainingSet.size()){
			System.err.println("k is larger than the size of the training set."); 
			System.exit(1);
		}

	}
	
	
	
	public void performClassification(){
		
		/**
		 * Helper class used for KNN classification.
		 */
		class KNNTuple implements Comparable<KNNTuple>{
			public String classValue;
			public double distance;
			
			public KNNTuple(String classValue, double distance){
				this.classValue = classValue;
				this.distance = distance;
			}
			
			public int compareTo(KNNTuple other){
				if(this.distance < other.distance) 			return -1;
				else if(this.distance == other.distance)	return 0;
				else 										return 1;
			}
		}
		
		// Perform classification
		PointSet.SimplePoint[] testPoints = testSet.getPoints();
		PointSet.SimplePoint[] trainingPoints = trainingSet.getPoints();
		for(int p = 0; p < testPoints.length; p++){
			
			// Create an array to store the KNN distances
			KNNTuple[] knnDistances = new KNNTuple[trainingPoints.length];
			PointSet.SimplePoint testPoint = testPoints[p];
			
			//Iterate through all of the training points.
			for(int j = 0; j < trainingPoints.length; j++)
				knnDistances[j] = new KNNTuple(trainingPoints[j].getActualClassValue(), calc.dist(testPoint, trainingPoints[j]));
			
			// Sort the knn points by ascending distance
			Arrays.sort(knnDistances);
	
			
			// Add the K best elements to the hash table.
			int correctClassCount = 0;
			Hashtable<String, Integer> kBest = new Hashtable<String, Integer>();
			for(int j = 0; j < this.k; j++){
				String key = knnDistances[j].classValue;
				Integer numbElements = kBest.get(key);
				// If this key does not yet exist in the hashtable, add it.
				if(numbElements == null) numbElements = 0;
				// Increment the number of elements.
				numbElements = numbElements.intValue() + 1;
				
				// Add the incremented element back to the hash table
				kBest.put(key, numbElements);
				
				if(key.equals(testPoint.getActualClassValue()))
					correctClassCount++;
			}
			
			
			
			// Find the highest scoring key
			Set<String> keys = kBest.keySet();
			String bestKey = "XXXXXXX"; 
			double highestVote = Double.MIN_VALUE;
			for(String key : keys){
				double keyVal = kBest.get(key);
				if(keyVal > highestVote){
					bestKey = key;
					highestVote = keyVal;
				}
			}
			
			// Store the test point's key.
			testPoint.setPredictedClassValue(bestKey);
			//Store the number of correct neighbors
			accuracyTable.put(Integer.toString(testPoint.getID()), correctClassCount);
		}
		
		// Output the KNN Results.
		this.outputResults();
		
	}
	
	
	public void outputResults(){
		try{
			BufferedWriter fileOut = new BufferedWriter(new FileWriter(outputFile)); // Open the file containing the algorithm comparison results.

			// Print the two header lines.
			fileOut.write( "k = " + this.k );fileOut.newLine();
			Class<? extends DistanceMetric> calcClass = this.calc.getClass();
			Field calcNameField = calcClass.getField("NAME");
			fileOut.write("distance = " + calcNameField.get(null)); 
			
			// Get all test points
			PointSet.SimplePoint[] testingPoints = testSet.getPoints();
			
			// Iterate through each class value and get its accuracy
			String[] classLabels = trainingSet.getAllActualClasses();
			for(int i = 0; i < classLabels.length; i++){
			
				int numbPoints = 0;
				int correctClassifications = 0;
				int correctNeighbors = 0, numbNeighbors = 0;
				for(int j = 0; j < testingPoints.length; j++){
					
					// See if the point has the currently active class label.
					if(testingPoints[j].getActualClassValue().equals(classLabels[i])){
						numbPoints++;
						numbNeighbors+=k;
						correctNeighbors += this.accuracyTable.get(Integer.toString(testingPoints[j].getID()));
						// See if the point is correctly classified.
						if(testingPoints[j].getActualClassValue().equals(testingPoints[j].getPredictedClassValue()))
							correctClassifications++;
					}
					
				}
				
				// Print the class accuracy
				fileOut.newLine();
				//fileOut.write("average accuracy for species " + (i+1) + " = " + (double)(correctClassifications)/numbPoints); 
				fileOut.write("average accuracy for species " + (i+1) + " = " + (double)(correctNeighbors)/numbNeighbors);
				
			}
				
			
			/*PointSet.SimplePoint[] pointsArr = this.testSet.getPoints();
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
