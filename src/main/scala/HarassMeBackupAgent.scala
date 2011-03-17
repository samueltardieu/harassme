package net.rfc1149.harassme

import _root_.android.app.backup.{BackupAgentHelper, SharedPreferencesBackupHelper}

class HarassMeBackupAgent extends BackupAgentHelper {

  // This is the name of the shared preferences file.
  val PREFS_NAME = "net.rfc1149.harassme_preferences"

  override def onCreate(): Unit =
    addHelper("prefs",
              new SharedPreferencesBackupHelper(this, PREFS_NAME))

}
