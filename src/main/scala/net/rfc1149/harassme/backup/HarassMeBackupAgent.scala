package net.rfc1149.harassme
package backup

import android.app.backup.{BackupAgentHelper, SharedPreferencesBackupHelper}

class HarassMeBackupAgent extends BackupAgentHelper {

  override def onCreate() =
    addHelper(Prefs.backupKey, new SharedPreferencesBackupHelper(this, Prefs.name))

}
