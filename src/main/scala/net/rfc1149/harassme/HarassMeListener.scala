package net.rfc1149.harassme

import android.content.{BroadcastReceiver, ContentResolver, Context, Intent}
import android.media.AudioManager._
import android.provider.CallLog.Calls
import android.telephony.TelephonyManager._
import org.scaloid.common.{SIntent, audioManager}

class HarassMeListener extends BroadcastReceiver {

  import net.rfc1149.harassme.HarassMeListener._

  override def onReceive(context: Context, intent: Intent) = {
    implicit val ctx = context
    val extras = intent.getExtras
    extras.getString(EXTRA_STATE) match {
      case EXTRA_STATE_RINGING =>
        // Believe it or not, java.lang.String#isEmpty is not available for API < 9.
        val number = Option(extras.getString(EXTRA_INCOMING_NUMBER)).filterNot(_.length == 0)
        if (number.exists(shouldBeSignaled) && isSilent)
          context.startService(SIntent[RingerService])
      case _ =>
        context.stopService(SIntent[RingerService])
    }
  }

}

object HarassMeListener {

  private def isSilent(implicit context: Context) =
    audioManager(context).getRingerMode != RINGER_MODE_NORMAL || audioManager(context).getStreamVolume(STREAM_RING) == 0

  // Return true if the maximum number of calls has been reached with the current one.

  private def shouldBeSignaled(number: String)(implicit context: Context): Boolean = {
    val prefs = new Prefs(context)
    prefs.serviceActivated &&
      missedCalls(context, number, System.currentTimeMillis - prefs.minutesCount * 60000) >= prefs.callCount - 1
  }

  private def missedCalls(context: Context, number: String, since: Long): Int = {
    val resolver = context.getContentResolver
    val lastAnswered = lastAnsweredCallFrom(resolver, number)
    missedCallsSince(resolver, number, since.max(lastAnswered getOrElse 0))
  }

  private[this] def lastAnsweredCallFrom(resolver: ContentResolver, number: String): Option[Long] =
    for {
      c <- Option(resolver.query(Calls.CONTENT_URI,
        Array(Calls.DATE),
        s"${Calls.NUMBER} = ? AND ${Calls.TYPE} = ${Calls.INCOMING_TYPE}",
        Array(number),
        s"${Calls.DATE} DESC"))
      if c.moveToFirst()
    } yield c.getLong(c.getColumnIndex(Calls.DATE))

  private[this] def missedCallsSince(resolver: ContentResolver, number: String, since: Long): Int =
    resolver.query(Calls.CONTENT_URI,
      Array(Calls.DATE),
      s"${Calls.NUMBER} = ? AND ${Calls.TYPE} = ${Calls.MISSED_TYPE} AND ${Calls.DATE} > ?",
      Array(number, since.toString),
      null).getCount

}
