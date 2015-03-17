import android.Keys._

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

android.Plugin.androidBuild

name := "Harass Me"

scalaVersion := "2.11.6"

proguardOptions in Android ++= Seq("-dontobfuscate", "-dontoptimize", "-keepattributes Signature",
  "-printseeds target/seeds.txt", "-printusage target/usage.txt",
  "-keep public class net.rfc1149.harassme.backup.HarassMeBackupAgent",
  "-dontwarn scala.collection.**" // required from Scala 2.11.4
)

scalacOptions in Compile ++= Seq("-feature", "-deprecation")
