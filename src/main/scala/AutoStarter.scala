package net.rfc1149.harassme

import _root_.android.content._

class AutoStarter extends BroadcastReceiver {

  // If we receive a broadcast intent (boot completed), start
  // the service if needed.
  override def onReceive(context: Context, intent: Intent) =
    HarassMeService.startService(context)

}
