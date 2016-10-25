import android.Keys._

android.Plugin.androidBuild

name := "Harass Me"

scalaVersion := "2.11.8"

proguardOptions in Android ++= Seq("-dontobfuscate", "-dontoptimize", "-keepattributes Signature",
  "-printseeds target/seeds.txt", "-printusage target/usage.txt",
  "-keep public class net.rfc1149.harassme.HarassMeBackupAgent",
  "-dontwarn scala.collection.**", // required from Scala 2.11.4
  "-dontwarn org.scaloid.common.TraitWebView$class"
)

proguardCache in Android += "org.scaloid"

libraryDependencies += "org.scaloid" %% "scaloid" % "4.2"

run <<= run in Android
install <<= install in Android

javacOptions in Compile ++= Seq("-source", "1.7", "-target", "1.7")
scalacOptions in Compile ++= Seq("-feature", "-deprecation")
