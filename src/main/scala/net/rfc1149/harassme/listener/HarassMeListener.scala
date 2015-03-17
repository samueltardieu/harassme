package net.rfc1149.harassme
package listener

import android.content.{BroadcastReceiver, Context, Intent}
import android.telephony.TelephonyManager._

class HarassMeListener extends BroadcastReceiver {

  private[this] def getRinger(context: Context) = new ringer.Ringer(context)

  override def onReceive(context: Context, intent: Intent) = {
    val extras = intent.getExtras
    val state = extras.getString(EXTRA_STATE)
    val number = extras.getString(EXTRA_INCOMING_NUMBER) match {
      case null  => None
      case ""    => None
      case other => Some(other)
    }
    state match {
      case EXTRA_STATE_RINGING =>
        if (number.exists(LastCalls.shouldBeSignaled(context, _)))
          getRinger(context).ringPhone
      case _ =>
        getRinger(context).unringPhone
    }
  }

}
