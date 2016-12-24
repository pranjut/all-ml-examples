package com.tekela.model

import java.io.File

import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

import scala.language.reflectiveCalls


object DecisionTreeForWeatherPlay {

   def prepareDecisionTreeModel(conf: SparkConf, sc: SparkContext): DecisionTreeModel = {
//     val conf: SparkConf = new SparkConf().setAppName("DecisionTreeClassificationExample").setMaster("local[*]")
//     val sc = new SparkContext(conf)

     // $example on$
     // Load and parse the data file.
     val dataFilePath = getClass.getResource("/data/mllib/sample_libsvm_data.txt").getPath
     val data = MLUtils.loadLibSVMFile(sc, dataFilePath)
     // Split the data into training and test sets (30% held out for testing)
     val splits = data.randomSplit(Array(0.7, 0.3))
     val (trainingData, testData) = (splits(0), splits(1))

     // Train a DecisionTree model.
     //  Empty categoricalFeaturesInfo indicates all features are continuous.
     val numClasses = 2
     val categoricalFeaturesInfo = Map[Int, Int]()
     val impurity = "gini"
     val maxDepth = 5
     val maxBins = 32

     val model = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
       impurity, maxDepth, maxBins)

     // Evaluate model on test instances and compute test error
     val labelAndPreds = testData.map { point =>
       println(s"Test data are ${point.label} ... ${point.features}")
       val prediction = model.predict(point.features)
       (point.label, prediction)
     }
     val testErr = labelAndPreds.filter(r => r._1 != r._2).count().toDouble / testData.count()
     println("Test Error = " + testErr)
     println("Learned classification tree model:\n" + model.toDebugString)

     // Save and load model
     val modelFile = new File("target/tmp/myDecisionTreeClassificationModel")
     if(modelFile.exists())
       modelFile.delete()

     model.save(sc, "target/tmp/myDecisionTreeClassificationModel")
     val sameModel = DecisionTreeModel.load(sc, "target/tmp/myDecisionTreeClassificationModel")
     // $example off$
     sameModel
   }
 }

// scalastyle:on println
