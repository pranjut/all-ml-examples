package com.tekela.dto


import scala.collection.immutable.ListMap


case class Node(label: Label, branches: ListMap[Any, Node])

case class Label(name: String, position: Int)
