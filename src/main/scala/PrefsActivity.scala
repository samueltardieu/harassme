package net.rfc1149.harassme
 
import android.content.{Context, Intent, SharedPreferences}
import android.os.Bundle
import android.preference._
import android.view.View
import android.widget.Toast

import ImplicitConversions._

class PrefsActivity extends PreferenceActivity with Notification with Prefs {

  val context = this

  def startService: Unit = {
    if (!HarassMeService.serviceRunning)
      short_toast(R.string.service_startup)
    HarassMeService.startService(this)
  }

  def stopService: Unit = {
    if (HarassMeService.serviceRunning)
      short_toast(R.string.service_stop)
    HarassMeService.stopService(this)
  }

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    if (serviceActivated)
      startService
    addPreferencesFromResource(R.layout.preferences)
    findPreference("serviceactivated").setOnPreferenceChangeListener ({
      v : Any =>
	if (v.asInstanceOf[Boolean])
	  startService
	else
	  stopService
    })
  }

  override def onPause = {
    super.onPause
    android.app.backup.BackupManager.dataChanged(Prefs.appli)
  }

}
