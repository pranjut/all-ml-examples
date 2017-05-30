package com.tekela.trainer

import breeze.linalg._
import breeze.numerics._
import com.tekela.network.{FeedForwardNetwork, NeuralNetwork}


object Backpropagation {

  /**
   * This function basically calculates the errors for the input that is being passed against the output we get.
   * So the fomulae for doing that is
   * J= 1/2 Sigma (y-y^^) where y is the targeted output and y^^ is the actual output
   * @param predictedOutput
   * @param actualOutput
   * @return
   */
  private def calculateCost(predictedOutput: DenseMatrix[Double], actualOutput: DenseMatrix[Double]): Double = {
    0.5 * sum(pow((predictedOutput - actualOutput), 2))
  }

  /**
   * This function will basically find the gradient by deriving the elements.
   * Basically f(z) = 1^^x/(1+e^^-zv) which the activation function we have inside the Neural network.
   * and in the derivation is defined as f``(z) = u``v - uv``/v^^2
   * = 0.v - 1. e^^-z / (1+e^^-z)^^2
   * = e^^-z/(1+e^^-z)^^2
   */
  private def deriveSigmoid(value: DenseMatrix[Double]): DenseMatrix[Double] = {
    exp(-value) :/ pow((1.0 :+ exp(-value)), 2)
  }

  def train(network: NeuralNetwork, input: DenseMatrix[Double], expectedOutput: DenseMatrix[Double]) = {

    network match {
      case ffn: FeedForwardNetwork => {
        val (outputHiddenLayer, output) = ffn.forward(input)

        println("=========================================================")
        println()
        println("The cost of the current output = " + calculateCost(expectedOutput, output))
        println("=========================================================")
        println()
        println(s"hidden layer output")
        println(outputHiddenLayer)
        println(s"Final Output")
        println(output)
        println("=========================================================")
        println()
        println()
        println("The new weights for layers")
        println("=========================================================")
        println()
        val (weight1, weight2) = propagateBackwards(input, expectedOutput, output, outputHiddenLayer, ffn.outputLayerWeights)
        println()
        println("============================= Weight1 addition ============================")
        println(weight1)
        println()
        println("============================== Weight2 addition ===========================")
        println(weight1)

        val newWeight1 = ffn.hiddenLayerWeights.map(w => w :+ weight1)
        val newWeight2 = ffn.outputLayerWeights :+ weight2

        val (_, output2) = ffn.forward(input, Some(newWeight1), Some(newWeight2))

        println("============================== output ===========================")
        println(output2)
        println()
        println("============================== cost2 ===========================")
        println(calculateCost(expectedOutput, output2))
      }
      case _ => println("Unsupported network")
    }
  }


  private def propagateBackwards(input: DenseMatrix[Double], predictedOutput: DenseMatrix[Double], actualOutput: DenseMatrix[Double],
                                 secondLayerOutput: DenseMatrix[Double], secondLayerWeights: DenseMatrix[Double]) = {

    val delta3 = calculateDeltaFor3rdLayer(input, predictedOutput, actualOutput)
    val dJdw2 = propagateBackToSecondLayer(delta3, secondLayerOutput) // Here dJdw2 is named with keep the thing in mind
    // that its the derivation of the error J with the rate of change of weight in second layer

    val delta2 = calculateDeltaFor2ndLayer(delta3, secondLayerOutput, secondLayerWeights)
    val dJdW1 = propagateToFirstLayer(input, delta2)
    // Here dJdw1 is named with keep the thing in mind
    // that its the derivation of the error J with the rate of change of weight in first layer

    (dJdW1, dJdw2)
  }

  /**
   * Basically we are calculating -(y-y^^)f``(z^^3) here.
   * Where y z3 is f(z^^3) the sigmoid function in the 3rd or output layer.
   * @param input
   * @param predictedOutput
   * @param actualOutput
   * @return
   */
  private def calculateDeltaFor3rdLayer(input: DenseMatrix[Double], predictedOutput: DenseMatrix[Double],
                                        actualOutput: DenseMatrix[Double]): DenseMatrix[Double] = {
    (-(predictedOutput - actualOutput) :* deriveSigmoid(actualOutput))
  }

  /**
   * So here we propagate the errors that we found in the 3rd layer to second layer. That simply means we are returning the
   * errors in respect to each weight from second layer. Here we multiply the delta that we find for the third or output
   * layer against the difference in outputs and derivation of activation function.
   *
   * @param delta3
   * @param secondLayerOutput
   * @return
   */
  private def propagateBackToSecondLayer(delta3: DenseMatrix[Double], secondLayerOutput: DenseMatrix[Double]) = {
    secondLayerOutput.t * delta3
  }

  /**
   * Similarly for 2nd layer delta we are multiplying the second layer's weight's transpose to the delta with the derived sigmoid
   * of the second layer output.
   * @param delta3
   * @param secondLayerOutput
   * @param secondLayerWeights
   * @return
   */
  private def calculateDeltaFor2ndLayer(delta3: DenseMatrix[Double], secondLayerOutput: DenseMatrix[Double],
                                        secondLayerWeights: DenseMatrix[Double]): DenseMatrix[Double] = {

    val weightsTranspose: DenseMatrix[Double] = secondLayerWeights.t
    val step1: DenseMatrix[Double] = delta3 * weightsTranspose
    step1 :* deriveSigmoid(secondLayerOutput)
  }

  /**
   * Here as usual we are calculating the weights for the first layer using the delta2.
   * @param input
   * @param delta2
   * @return
   */
  private def propagateToFirstLayer(input: DenseMatrix[Double], delta2: DenseMatrix[Double]) = {
    input.t * delta2
  }

}
