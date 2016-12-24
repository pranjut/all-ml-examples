package com.tekela.boot

import com.tekela.model.DecisionTreeForWeatherPlay._
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.mllib.util.MLUtils

object Boot extends App{

  println("Hello world")

  val conf = new SparkConf().setAppName("DecisionTreeWeatherForPlay").setMaster("local[*]")
  val sc = new SparkContext(conf)

  val dtMod = prepareDecisionTreeModel(conf, sc)


//  /*testData.map { point =>
//    val prediction = dtModel.predict(point.features)
//    (point.label, prediction)
//  }*/

}
