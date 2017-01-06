import Dependencies._
import CommonSettings._

name := """all-ml-examples"""

version := "1.0"

scalaVersion := scala

lazy val root = (
  project.in(file("."))
    aggregate(dtSparkDefaultExample)
  )


lazy val dtSparkDefaultExample = (
  BaseProject("dt-spark-default-example")
    settings (libraryDependencies += sparkMlLib)
  )

lazy val dtPOSO = (
  BaseProject("dt-poso")
    settings (libraryDependencies += breeze)
  )


// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

//resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"

//libraryDependencies += "com.github.mandar2812.DynaML" % "dynaml-core_2.11" % "v1.4.1-beta.6"
//
//resolvers += "jitpack" at "https://jitpack.io"
