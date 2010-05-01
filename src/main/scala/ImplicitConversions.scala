package net.rfc1149.harassme

import _root_.android.preference._

object ImplicitConversions {

  implicit def toOnPreferenceChangeListener
      (f : Any => _) : Preference.OnPreferenceChangeListener = {
    new Preference.OnPreferenceChangeListener {
      override def onPreferenceChange(preference : Preference,
				      newValue : Object) = {
	f(newValue)
	true
      }
    }
  }

}
