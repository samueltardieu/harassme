import sbt._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "Harass Me",
    version := "1.16",
    versionCode := 116,
    scalaVersion := "2.9.1",
    platformName in Android := "android-15"
  )

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++
    Seq (
      keyalias in Android := "rfc1149",
      libraryDependencies += "org.scalatest" %% "scalatest" % "1.6.1" % "test",
      proguardOptimizations in Android := Seq("-optimizations !code/simplification/arithmetic")
    )
}

object AndroidBuild extends Build {
  lazy val main = Project (
    "Harass Me",
    file("."),
    settings = General.fullAndroidSettings
  )

  lazy val tests = Project (
    "tests",
    file("tests"),
    settings = General.settings ++ AndroidTest.androidSettings
  ) dependsOn main
}
