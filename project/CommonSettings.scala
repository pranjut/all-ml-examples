import sbt.Keys._
import sbt._

object CommonSettings {

  val projectSettings = Seq(
    organization := "com.tekela",
    scalaVersion := Dependencies.scala,
    resolvers ++= Dependencies.resolvers,
    fork in Test := true,
    parallelExecution in Test := true,
    checksums in update := Nil
  )

  def BaseProject(name: String): Project = (
    Project(name, file(name))
      settings(projectSettings:_*)
    )

  def SBTProject(name: String): Project = (
    BaseProject(name)
    )

}
