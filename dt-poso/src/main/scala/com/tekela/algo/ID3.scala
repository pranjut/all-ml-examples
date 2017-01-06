package com.tekela.algo

import breeze.linalg.DenseMatrix
import com.tekela.dto.Attribute

class ID3 {

  def train(examples: DenseMatrix, targetAttributes: DenseMatrix): Unit = {

    val numberOfCols = examples.cols

    val attributes: List[Attribute] = (0 to numberOfCols).toList.map {
      col =>
        examples(::, col)
        Attribute(examples(0, col).toString, examples(::, col).toArray.toList, col)
    }


  }

  /**
   * Gain stands for information gain. which is used for calculating the root node as well, partition of later nodes.
   *The formula for gain is Entropy(s) - v E {Weak, Strong} M |Sv|/|S|Entropy(Sv). Here Sigma symbol is unavailable so
   * writing like this. basically it goes like this. v belongs to {Weak and Strong}, M denotes the sigma symbol, then Rest
   * of the function is understandable I guess
   * @param attributes
   * @param targetAttributes
   * @param attributeInMatrix
   */
  def gain(attributes: DenseMatrix, targetAttributes: DenseMatrix, attributeInMatrix: Int) = {
    val tgAttribs = targetAttributes(::, 0).toArray.toList
    val results = tgAttribs.distinct
    val (resPositive, resNegative) = tgAttribs.partition(attr => attr == results.head)
    val totalRes = resPositive.size + resNegative.size
    val entropyS = calculateEntropy(resPositive.size, resNegative.size)

    val attribs = attributes(::, attributeInMatrix).toArray.toList
    val attribsTgsMap = (attribs zip tgAttribs).groupBy(_._1)

    // As we have to calculate Sigma, we are already calculating the functionn inside of it. i.e. we calculate the entropy
    //for each attribute, as well as the portion of total attribute results / total results
    val gainSigmaElements = attribsTgsMap.map{
      case (att, attTgZip) =>
        val (attPositives, attNegatives) = attTgZip.partition{ case (att, tg)=> tg == results.head}
        val entropyAtt = calculateEntropy(attPositives.size, attNegatives.size)
        (attTgZip.size / totalRes) * entropyAtt
    }.toList

    entropyS - gainSigmaElements.sum
  }

  /**
   * This is the normal formula of entropy, it will be applicable for both entropy on total number and entropy on
   * set of attributes. It is basically doing the calculation -p(+)log2p(+) - p(-)log2p(-)
   *
   * Entropy(S) = -p(+)log2p(+) - p(-)log2p(-)
   * where S is the collection of elements on which we are doing the classification.
   * p is the portion of the one particular set of result to the whole result set, e.g. p(+) = Sweak/S.
   *
   * @param positives
   * @param negatives
   * @return
   */
  def calculateEntropy(positives: Int, negatives: Int) = {
    val total = positives + negatives
    val positivePartition = positives / total
    val negativePartition = negatives / total
    (-1 * positivePartition * log2(positivePartition)) - (negativePartition * log2(negativePartition))
  }

  val lnOf2 = scala.math.log(2)

  // natural log of 2
  def log2(x: Double): Double = scala.math.log(x) / lnOf2

}
