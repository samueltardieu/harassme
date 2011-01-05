package net.rfc1149.harassme

import _root_.android.app.Activity
import _root_.android.content.Context
import _root_.android.content.SharedPreferences
import _root_.android.preference.PreferenceManager

trait Prefs {

  private def sp(context: Context) =
    PreferenceManager.getDefaultSharedPreferences(context)

  def serviceActivated(context: Context) =
    sp(context).getBoolean("serviceactivated", false)

  def callCount(context: Context) =
    Prefs.makeInt(sp(context).getString("callcount", ""), 3)

  def minutesCount(context: Context) =
    Prefs.makeInt(sp(context).getString("minutescount", ""), 3)

}

object Prefs {

  def makeInt(s: String, default: Int) =
    if (s == "")
      default
    else
      s.toInt

}
