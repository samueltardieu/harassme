package net.rfc1149.harassme

import _root_.android.content.Context

trait Prefs {

  private def sp(context: Context) =
    context.getSharedPreferences(Prefs.name, Context.MODE_PRIVATE)

  def serviceActivated(context: Context) =
    sp(context).getBoolean("serviceactivated", true)

  def callCount(context: Context) =
    sp(context).getString("callcount", "3").toInt

  def minutesCount(context: Context) =
    sp(context).getString("minutescount", "3").toInt

}

object Prefs {

  val name = "net.rfc1149.harassme_preferences"

  val backupKey = "prefs"

}
