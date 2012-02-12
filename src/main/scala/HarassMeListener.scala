package net.rfc1149.harassme
package listener

import android.content.{BroadcastReceiver, Context, Intent}
import android.telephony.{PhoneStateListener, TelephonyManager}

import ringer.Ringer

class HarassMeListener extends BroadcastReceiver {

  import TelephonyManager._

  private def ringer(context: Context) = new Ringer(context)

  override def onReceive(context: Context, intent: Intent) {
    val extras = intent.getExtras
    val state = extras.getString(EXTRA_STATE)
    val number = extras.getString(EXTRA_INCOMING_NUMBER) match {
	case null  => None
	case ""    => None
	case other => Some(other)
    }
    state match {
	case EXTRA_STATE_RINGING =>
	  if (number.map(LastCalls.shouldBeSignaled(context, _)) getOrElse false)
	    ringer(context).ringPhone
	case _ =>
	  ringer(context).unringPhone
    }
  }

}
