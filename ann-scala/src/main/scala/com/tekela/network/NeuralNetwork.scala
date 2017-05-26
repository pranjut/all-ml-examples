package com.tekela.network

import breeze.linalg._
import breeze.numerics._
import breeze.math._

trait NeuralNetwork {

  val layers: Layers

  lazy val hiddenLayerWeights = layers.hidden.map(num => DenseMatrix.rand(layers.input, num))
  lazy val outputLayerWeights = DenseMatrix.rand(layers.hidden.last, layers.output)

  def forward(input: DenseMatrix[Double]) = {
    println(s"Input weights ${hiddenLayerWeights.head}")
    val inputWeightHiddenLayer: DenseMatrix[Double] = input * hiddenLayerWeights.head
    println()
    println()
    println(s"After applying hidden layer weights ${inputWeightHiddenLayer}")
    val outputHiddenLayer = sigmoid(inputWeightHiddenLayer)
    println()
    println()
    println(s"After applying activation function input neurons $outputHiddenLayer")
    val inputWeightOutputLayer = outputHiddenLayer * outputLayerWeights
    println()
    println()
    println(s"Second layer matrix ............. ${inputWeightOutputLayer}")
    val output = sigmoid(inputWeightOutputLayer)
    println(s"output $output")
    output
  }

  def sigmoid(value: DenseMatrix[Double]): DenseMatrix[Double] = {
    1.0 :/ (1.0 :+ exp(-value))
  }

}

object NeuralNetworkFactory{
  def apply(neuronLayers: Layers) = new NeuralNetwork {
    val layers: Layers = neuronLayers
  }
}