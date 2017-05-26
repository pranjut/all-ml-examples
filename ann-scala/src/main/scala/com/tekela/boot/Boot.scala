package com.tekela.boot

import breeze.linalg.{DenseVector, DenseMatrix}
import com.tekela.network.{Layers, NeuralNetworkFactory}
import com.tekela.plot.BarChartFactory
import com.tekela.trainer.BackpropagationTrainer

object Boot extends App with BackpropagationTrainer{

  println("Welcome to neural network")
  val network = NeuralNetworkFactory(Layers(2, List(3), 1))
  val input = DenseMatrix((3.0, 5.0),(5.0,1.0), (10.0,2.0))
  val expected = DenseMatrix(78.0, 82.0, 93.0)
  val (outputHiddenLayer, output) = network.forward(input)

  println("=========================================================")
  println()
  println("The cost of the current output = " + calculateCost(expected, output))
  println("=========================================================")
  println()
  println(s"hidden layer output ${outputHiddenLayer}")
  println(s"output ${output}")
  println("=========================================================")
  println()
  println()
  println("The new weights for layers")
  println("=========================================================")
  println()
  val (weight1, weight2) = propagateBackwards(input, expected, output, outputHiddenLayer, network.outputLayerWeights)
  println()
  println("============================= Weight1 addition ============================")
  println(weight1)
  println()
  println("============================== Weight2 addition ===========================")
  println(weight1)

  val newWeight1 =network.hiddenLayerWeights.map(w => w :+ weight1)
  val newWeight2 =network.outputLayerWeights :+ weight2

  val (_, output2) = network.forward(input, Some(newWeight1), Some(newWeight2))

  println("============================== output ===========================")
  println(output2)
  println()
  println("============================== cost2 ===========================")
  println(calculateCost(expected, output2))

  BarChartFactory().draw(expected.toDenseVector, output.toDenseVector)

}
