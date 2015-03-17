package net.rfc1149.harassme
package prefs

import android.preference._
import scala.language.implicitConversions

object ImplicitConversions {

  implicit class toOnPreferenceChangeListener(f : Any => _) extends Preference.OnPreferenceChangeListener {
    override def onPreferenceChange(preference : Preference, newValue : Object) = {
      f(newValue)
      true
    }
  }

}
