package com.tekela.dto

case class Attribute(
                     name: String,
                     values: List[Any],
                     columnPosition: Int
                       ) {
  def getAttributeWithUniqueValues = {
    this.copy(values = values.distinct)
  }
}
