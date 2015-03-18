package net.rfc1149.harassme

import android.content.Context
import android.preference._

class Prefs(context: Context) {

  private[this] lazy val sp = context.getSharedPreferences(Prefs.name, Context.MODE_PRIVATE)

  def serviceActivated = sp.getBoolean("serviceactivated", true)

  def callCount = sp.getString("callcount", "3").toInt

  def minutesCount = sp.getString("minutescount", "3").toInt

  Prefs.restoreDefaultPreferences(context)

}

object Prefs {

  val appli = "net.rfc1149.harassme"

  val name = appli + "_preferences"

  val backupKey = "prefs"

  def restoreDefaultPreferences(implicit context: Context) =
    PreferenceManager.setDefaultValues(context, Prefs.name, Context.MODE_PRIVATE, R.xml.preferences, false)

}
