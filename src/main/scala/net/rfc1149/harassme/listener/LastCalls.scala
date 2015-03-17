package net.rfc1149.harassme
package listener

import android.content.Context

object LastCalls {

  // Return true if the maximum number of calls has been reached with
  // the current one.
  def shouldBeSignaled(context: Context, number: String): Boolean = {
    val prefs = new Prefs(context)
    prefs.serviceActivated && {
      val limit = System.currentTimeMillis - prefs.minutesCount * 60000
      val missed = MissedCalls.missedCalls(context, number, limit)
      missed >= prefs.callCount - 1
    }
  }

}
