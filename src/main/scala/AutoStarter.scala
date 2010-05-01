package net.rfc1149.harassme

import _root_.android.content._

class AutoStarter extends BroadcastReceiver with Prefs {

  override def onReceive(context: Context, intent: Intent) = {
    if (serviceActivated(context))
      HarassMeService.startService(context)
  }

}
