package net.rfc1149.harassme
package listener

import android.content.{ContentResolver, Context}
import android.provider.CallLog.Calls

object MissedCalls {

  private def lastAnsweredCallFrom(resolver: ContentResolver, number: String): Option[Long] = {
    val c = resolver.query(Calls.CONTENT_URI,
			   Array(Calls.DATE),
			   "Calls.NUMBER=? AND Calls.TYPE=1", Array(number),
			   "Calls.DATE DESC")
    if (c == null || c.getCount < 1)
      None
    else {
      c.moveToNext
      Some(c.getLong(c.getColumnIndex(Calls.DATE)))
    }
  }

  private def missedCallsSince(resolver: ContentResolver, number: String, since: Long): Int = {
    val c = resolver.query(Calls.CONTENT_URI,
			   Array(Calls.DATE),
			   "Calls.NUMBER=? AND Calls.TYPE=3 AND Calls.DATE>?", Array(number, since.toString),
			   null)
    c.getCount
  }

  def missedCalls(context: Context, number: String, since: Long): Int = {
    val resolver = context.getContentResolver
    val lastAnswered = lastAnsweredCallFrom(resolver, number)
    missedCallsSince(resolver, number, since.max(lastAnswered getOrElse 0))
  }

}
