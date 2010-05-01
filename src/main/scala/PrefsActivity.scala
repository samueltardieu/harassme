package net.rfc1149.harassme
 
import _root_.android.content.{Context, Intent, SharedPreferences}
import _root_.android.os.Bundle
import _root_.android.preference._
import _root_.android.view.View
import _root_.android.widget.Toast

import ImplicitConversions._

class PrefsActivity extends PreferenceActivity with Notification with Prefs {

  def startService: Unit = {
    Watcher.startService(this)
    short_toast(R.string.service_startup)
  }

  def stopService: Unit = {
    Watcher.stopService(this)
    short_toast(R.string.service_stop)
  }

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    if (serviceActivated(this) && !Watcher.serviceRunning)
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

}
