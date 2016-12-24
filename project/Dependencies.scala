import sbt._

object Dependencies {

  val scala = "2.11.8"

  val resolvers = DefaultOptions.resolvers(snapshot = true) ++ Seq(
    "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
  )

  val sparkMlLib = "org.apache.spark" % "spark-mllib_2.11" % "2.0.2"

  val mlLibDependencies: Seq[ModuleID] = Seq(
    sparkMlLib
  )

}
