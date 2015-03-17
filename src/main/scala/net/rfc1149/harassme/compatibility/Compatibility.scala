package net.rfc1149.harassme
package compatibility

object Compatibility {

  // Call Android facilities in a backward compatible way

  def dataChanged() =
    try {
      val cl = Class.forName("android.app.backup.BackupManager")
      val m = cl.getDeclaredMethod("dataChanged", classOf[String])
      m.invoke(null, Prefs.appli)
    } catch {
      case _: Exception =>
	// Do nothing if this method is not available (older SDK)
    }

}

