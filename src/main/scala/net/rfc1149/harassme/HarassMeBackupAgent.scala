package net.rfc1149.harassme

import android.app.backup.{BackupAgentHelper, SharedPreferencesBackupHelper}

class HarassMeBackupAgent extends BackupAgentHelper {

  override def onCreate() =
    addHelper(Prefs.backupKey, new SharedPreferencesBackupHelper(this, Prefs.name))

}
