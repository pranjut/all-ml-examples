import sbt._

object Dependencies {

  val scala = "2.11.8"
  val scalaTestVersion = "3.0.1"
  val mockitoVersion = "1.10.19"
  val breezeVersion = "0.12"

  val resolvers = DefaultOptions.resolvers(snapshot = true) ++ Seq(
    "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
  )


  def compile(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")

  def provided(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")

  def test(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")

  def it(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "it")

  val sparkMlLib = "org.apache.spark" % "spark-mllib_2.11" % "2.0.2"

  val breezeCore = "org.scalanlp" %% "breeze" % breezeVersion
  val breezeNative = "org.scalanlp" %% "breeze-natives" % breezeVersion
  val breezeViz= "org.scalanlp" %% "breeze-viz" % breezeVersion

  val breeze: Seq[ModuleID] = Seq(breezeCore, breezeNative, breezeViz)

  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion

  val mockito = "org.mockito" % "mockito-core" % mockitoVersion


  val mlLibDependencies: Seq[ModuleID] = Seq(
    sparkMlLib
  )

}
