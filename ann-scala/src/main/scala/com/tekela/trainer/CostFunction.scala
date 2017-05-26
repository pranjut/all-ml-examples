package com.tekela.trainer

import breeze.linalg._
import breeze.numerics._


trait CostFunction {

  def calculate(predictedOutput: DenseMatrix[Double], actualOutput: DenseMatrix[Double]): Double = {
    0.5 * sum(pow((predictedOutput - actualOutput), 2))
  }

  def gradientSigmoid(value: DenseMatrix[Double]): DenseMatrix[Double] = {
    exp(-value) :/ pow((1.0 :+ exp(-value)), 2)
  }

  def costDerivation(input: DenseMatrix[Double], predictedOutput: DenseMatrix[Double], actualOutput: DenseMatrix[Double],
                     secondLayerOutput: DenseMatrix[Double], secondLayerWeights: DenseMatrix[Double]) = {

    val delta3 = (-(predictedOutput - actualOutput) :* gradientSigmoid(actualOutput))
    val djdw2 = secondLayerOutput.t * delta3

    val delta2 = (delta3 * secondLayerWeights.t) * gradientSigmoid(secondLayerOutput)
    val dJdW1 = input * delta2
    (dJdW1, djdw2)
  }

}
