package net.rfc1149.harassme

import android.annotation.TargetApi
import android.app.backup.{BackupAgentHelper, SharedPreferencesBackupHelper}

@TargetApi(8)
class HarassMeBackupAgent extends BackupAgentHelper {

  override def onCreate() =
    addHelper(Prefs.backupKey, new SharedPreferencesBackupHelper(this, Prefs.name))

}
