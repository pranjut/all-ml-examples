package com.tekela.plot

import breeze.linalg._
import breeze.plot._

trait BarChart {

  def draw(xAxis: DenseVector[Double], yAxis: DenseVector[Double]) = {
    val fig = Figure()
    val plt = fig.subplot(2, 1, 1)
    plt += hist(yAxis)
    plt.title = "Bar chart"
    fig.refresh()
  }

}

object BarChartFactory {
  def apply() = new BarChart {}
}