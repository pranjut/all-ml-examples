package com.tekela.boot

import breeze.linalg.DenseMatrix
import com.tekela.network.{FeedForwardNetworkFactory, Layers}
import com.tekela.trainer.Backpropagation

object Boot extends App{

  println("Welcome to neural network")
  val network = FeedForwardNetworkFactory(Layers(2, List(3), 1))
  val input = DenseMatrix((3.0, 5.0),(5.0,1.0), (10.0,2.0))
  val expected = DenseMatrix(78.0, 82.0, 93.0)

  Backpropagation.train(network, input, expected)

//  BarChartFactory().draw(expected.toDenseVector, output.toDenseVector)

}
