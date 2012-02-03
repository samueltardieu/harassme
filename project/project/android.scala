import sbt._
object PluginDef extends Build {
    override lazy val projects = Seq(root)
    lazy val root = Project("plugins", file(".")) dependsOn(androidPlugin)
    lazy val androidPlugin = uri("git://github.com/jberkel/android-plugin.git")
}
