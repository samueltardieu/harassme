package net.rfc1149.harassme

import android.content.Context

trait Prefs {

  val context: Context

  private lazy val sp =
    context.getSharedPreferences(Prefs.name, Context.MODE_PRIVATE)

  def serviceActivated =
    sp.getBoolean("serviceactivated", true)

  def callCount =
    sp.getString("callcount", "3").toInt

  def minutesCount =
    sp.getString("minutescount", "3").toInt

}

object Prefs {

  val appli = "net.rfc1149.harassme"

  val name = appli + "_preferences"

  val backupKey = "prefs"

}
