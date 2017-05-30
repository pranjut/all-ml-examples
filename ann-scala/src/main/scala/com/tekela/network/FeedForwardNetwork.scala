package com.tekela.network

import breeze.linalg._
import breeze.numerics._
import breeze.math._

trait FeedForwardNetwork extends NeuralNetwork{

  val layers: Layers

  lazy val hiddenLayerWeights = layers.hidden.map(num => DenseMatrix.rand[Double](layers.input, num))
  lazy val outputLayerWeights = DenseMatrix.rand[Double](layers.hidden.last, layers.output)

  def forward(input: DenseMatrix[Double], hiddenWeights: Option[List[DenseMatrix[Double]]] = None,
              outputWeights: Option[DenseMatrix[Double]] = None) = {
//    println(s"Input weights ${hiddenLayerWeights.head}")
    val inputWeightHiddenLayer: DenseMatrix[Double] = input * hiddenWeights.map(ws => ws.head).getOrElse(hiddenLayerWeights.head)
//    println()
//    println()
//    println(s"After applying hidden layer weights ${inputWeightHiddenLayer}")
    val outputHiddenLayer = sigmoid(inputWeightHiddenLayer)
//    println()
//    println()
//    println(s"After applying activation function input neurons $outputHiddenLayer")
    val inputWeightOutputLayer = outputHiddenLayer * outputWeights.getOrElse(outputLayerWeights)
//    println()
//    println()
//    println(s"Second layer matrix ............. ${inputWeightOutputLayer}")
    val output = sigmoid(inputWeightOutputLayer)
//    println(s"output $output")
    (outputHiddenLayer, output)
  }

  def sigmoid(value: DenseMatrix[Double]): DenseMatrix[Double] = {
    1.0 :/ (1.0 :+ exp(-value))
  }

}

object FeedForwardNetworkFactory{
  def apply(neuronLayers: Layers) = new FeedForwardNetwork {
    val layers: Layers = neuronLayers
  }
}