#!/bin/bash

export SUBMISSION_DIR=LAB3_SUBMISSION
export EXERCISE1_DIR=E1
export EXERCISE2_DIR=E2
export EXERCISE3_DIR=E3
export LAB_DIRECTORY=/user/user01

# Delete the submission directory then remake it
echo "Deleting the existing submission directory."
rm -rf $SUBMISSION_DIR
echo "Making a new submission directory."
mkdir $SUBMISSION_DIR

# Build Exercise #1
echo "Copying the files for exercise #1"
mkdir -p $SUBMISSION_DIR/$EXERCISE1_DIR
cp KMeans_KNN/src/cs286/rebuild_KMeans.sh $SUBMISSION_DIR/$EXERCISE1_DIR/rebuild.sh
cp KMeans_KNN/src/cs286/rerun_KMeans.sh $SUBMISSION_DIR/$EXERCISE1_DIR/rerun.sh
cp KMeans_KNN/src/cs286/PointSet.java $SUBMISSION_DIR/$EXERCISE1_DIR/
cp KMeans_KNN/src/cs286/Kmeans.java $SUBMISSION_DIR/$EXERCISE1_DIR/
cp KMeans_KNN/manifests/kmeans_manifest.txt $SUBMISSION_DIR/$EXERCISE1_DIR/MANIFEST.MF
cp kmeans_test.sh $SUBMISSION_DIR/$EXERCISE1_DIR/
cp iris-data-labeled.txt $SUBMISSION_DIR/$EXERCISE1_DIR/iris-data.txt
cp -r verification_results $SUBMISSION_DIR/$EXERCISE1_DIR/

# Build Exercise #2
echo "Copying the files for exercise #2"
mkdir -p $SUBMISSION_DIR/$EXERCISE2_DIR
cp KMeans_KNN/src/cs286/rebuild_KNN.sh $SUBMISSION_DIR/$EXERCISE2_DIR/rebuild.sh
cp KMeans_KNN/src/cs286/rerun_KNN.sh $SUBMISSION_DIR/$EXERCISE2_DIR/rerun.sh
cp KMeans_KNN/src/cs286/PointSet.java $SUBMISSION_DIR/$EXERCISE2_DIR/
cp KMeans_KNN/src/cs286/Knn.java $SUBMISSION_DIR/$EXERCISE2_DIR/
cp KMeans_KNN/manifests/knn_manifest.txt $SUBMISSION_DIR/$EXERCISE2_DIR/MANIFEST.MF
cp knn_test.sh $SUBMISSION_DIR/$EXERCISE2_DIR/
cp iris-data-labeled.txt $SUBMISSION_DIR/$EXERCISE2_DIR/iris-data.txt
cp -r verification_results $SUBMISSION_DIR/$EXERCISE2_DIR/

# Copy the files into the
echo "Creating the lab directory."
rm -rf $LAB_DIRECTORY/$SUBMISSION_DIR &> /dev/null
cp -r $SUBMISSION_DIR $LAB_DIRECTORY

# Build the submission zip file
zip $SUBMISSION_DIR.zip $SUBMISSION_DIR &> /dev/null

# End of the Bash creation script
echo "Submission building completed successfully."
