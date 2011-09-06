package net.rfc1149.harassme

import android.content._

class AutoStarter extends BroadcastReceiver with Prefs {

  // If we receive a broadcast intent (boot completed), start
  // the service if needed.
  override def onReceive(context: Context, intent: Intent) =
    if (serviceActivated(context))
      HarassMeService.startService(context)

}
