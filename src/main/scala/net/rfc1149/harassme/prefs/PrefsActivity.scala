package net.rfc1149.harassme
package prefs

import android.os.Bundle
import android.preference._
import net.rfc1149.harassme.compatibility.Compatibility

class PrefsActivity extends PreferenceActivity {

  val context = this

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    Prefs.restoreDefaultPreferences(this)
    addPreferencesFromResource(R.xml.preferences)
  }

  override def onPause() = {
    super.onPause()
    Compatibility.dataChanged()
  }

}
