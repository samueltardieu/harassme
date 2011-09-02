package net.rfc1149.harassme

import _root_.android.app.backup.{BackupAgentHelper, SharedPreferencesBackupHelper}

class HarassMeBackupAgent extends BackupAgentHelper {

  import HarassMeBackupAgent._

  override def onCreate(): Unit =
    addHelper(PREFS_BACKUP_KEY,
              new SharedPreferencesBackupHelper(this, PREFS_NAME))

}

object HarassMeBackupAgent {

  // This is the name of the shared preferences file.
  val PREFS_NAME = "net.rfc1149.harassme_preferences"

  val PREFS_BACKUP_KEY = "prefs"

}
