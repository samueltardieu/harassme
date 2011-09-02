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
    sp(context).getString("callcount", "3").toInt

  def minutesCount(context: Context) =
    sp(context).getString("minutescount", "3").toInt

}
