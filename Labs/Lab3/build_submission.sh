#!/bin/bash

export SUBMISSION_DIR=LAB3_SUBMISSION
export EXERCISE1_DIR=E1
export EXERCISE2_DIR=E2
export EXERCISE3_DIR=E3


# Delete the submission directory then remake it
echo "Deleting the existing submission directory."
rm -rf $SUBMISSION_DIR
echo "Making a new submission directory."
mkdir $SUBMISSION_DIR

# Build Exercise #1
echo "Copying the files for exercise #1"
mkdir -p $SUBMISSION_DIR/$EXERCISE1_DIR
cp KMeans_KNN/src/lab3/rebuild_KMeans.sh $SUBMISSION_DIR/$EXERCISE1_DIR/rebuild.sh
cp KMeans_KNN/src/lab3/rerun_KMeans.sh $SUBMISSION_DIR/$EXERCISE1_DIR/rerun.sh
cp KMeans_KNN/src/lab3/PointSet.java $SUBMISSION_DIR/$EXERCISE1_DIR/
cp KMeans_KNN/src/lab3/KMeans.java $SUBMISSION_DIR/$EXERCISE1_DIR/

# Build Exercise #2
echo "Copying the files for exercise #2"
mkdir -p $SUBMISSION_DIR/$EXERCISE2_DIR
cp KMeans_KNN/src/lab3/rebuild_KNN.sh $SUBMISSION_DIR/$EXERCISE1_DIR/rebuild.sh
cp KMeans_KNN/src/lab3/rerun_KNN.sh $SUBMISSION_DIR/$EXERCISE1_DIR/rerun.sh
cp KMeans_KNN/src/lab3/PointSet.java $SUBMISSION_DIR/$EXERCISE1_DIR/
cp KMeans_KNN/src/lab3/KNN.java $SUBMISSION_DIR/$EXERCISE1_DIR/

# Build Exercise #3
mkdir -p $SUBMISSION_DIR/$EXERCISE3_DIR


echo "Submission building completed successfully."
