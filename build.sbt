import android.Keys._

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

android.Plugin.androidBuild

name := "Harass Me"

scalaVersion := "2.11.6"

proguardOptions in Android ++= Seq("-dontobfuscate", "-dontoptimize", "-keepattributes Signature",
  "-printseeds target/seeds.txt", "-printusage target/usage.txt",
  "-keep public class net.rfc1149.harassme.HarassMeBackupAgent",
  "-dontwarn scala.collection.**" // required from Scala 2.11.4
)

proguardCache in Android ++= Seq(
  ProguardCache("org.scaloid") % "org.scaloid"
)

libraryDependencies += "org.scaloid" %% "scaloid" % "3.6.1-10" withSources() withJavadoc()

run <<= run in Android

install <<= install in Android

scalacOptions in Compile ++= Seq("-feature", "-deprecation")

apkSigningConfig in Android <<= (properties in Android) { props =>
  for (store <- some(props.getProperty("key.store"));
       alias <- some(props.getProperty("key.alias")))
    yield android.PromptStorepassSigningConfig(new File(store), alias)
}
