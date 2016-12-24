name := """scala-decision-tree"""

version := "1.0"

scalaVersion := "2.11.8"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(

  "org.apache.spark" % "spark-mllib_2.11" % "2.0.2"
)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"

//libraryDependencies += "com.github.mandar2812.DynaML" % "dynaml-core_2.11" % "v1.4.1-beta.6"
//
//resolvers += "jitpack" at "https://jitpack.io"
