package net.rfc1149.harassme
package prefs

import android.content.{Context, Intent, SharedPreferences}
import android.os.Bundle
import android.preference._
import android.view.View

import ImplicitConversions._

import compatibility.Compatibility

class PrefsActivity extends PreferenceActivity {

  val context = this

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.layout.preferences)
  }

  override def onPause = {
    super.onPause
    Compatibility.dataChanged
  }

}
