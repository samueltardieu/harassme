package net.rfc1149.harassme

import android.os.Bundle
import android.preference._
import org.scaloid.common.SActivity

class PrefsActivity extends PreferenceActivity with SActivity {

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    Prefs.restoreDefaultPreferences
    addPreferencesFromResource(R.xml.preferences)
  }

  override def onPause() = {
    super.onPause()
    Compatibility.dataChanged()
  }

}
