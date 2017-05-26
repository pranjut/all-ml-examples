package com.tekela.boot

import breeze.linalg.{DenseVector, DenseMatrix}
import com.tekela.network.{Layers, NeuralNetworkFactory}
import com.tekela.plot.BarChartFactory
import com.tekela.trainer.CostFunction

object Boot extends App with CostFunction{

  println("Welcome to neural network")
  val expected = DenseMatrix(78.0, 82.0, 93.0)
  val result = NeuralNetworkFactory(Layers(2, List(3), 1)).forward(DenseMatrix((3.0, 5.0),(5.0,1.0), (10.0,2.0)))

  println("The cost of the current output = " + calculate(expected, result))

  BarChartFactory().draw(expected.toDenseVector, result.toDenseVector)

}
