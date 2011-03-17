import sbt._

trait Defaults {
  def androidPlatformName = "android-11"
}

class HarassMe(info: ProjectInfo) extends ParentProject(info) {
  override def shouldCheckOutputDirectories = false
  override def updateAction = task { None } 

  lazy val main  = project(".", "HarassMe", new MainProject(_))
  lazy val tests = project("tests",  "tests", new TestProject(_), main)

  class MainProject(info: ProjectInfo) extends AndroidProject(info) with MarketPublish with Defaults {    

    val keyalias = "rfc1149"

  }

  class TestProject(info: ProjectInfo) extends AndroidTestProject(info) with Defaults
}
