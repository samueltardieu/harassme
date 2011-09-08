package net.rfc1149.harassme

import android.content._

class AutoStarter extends BroadcastReceiver {

  // If we receive a broadcast intent (boot completed), start
  // the service if needed.
  override def onReceive(ctx: Context, intent: Intent) = {
    val prefs = new Prefs { val context = ctx }
    if (prefs.serviceActivated)
      HarassMeService.startService(ctx)
  }

}
