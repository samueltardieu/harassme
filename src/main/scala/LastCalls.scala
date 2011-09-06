package net.rfc1149.harassme

import android.content.Context
import java.util.Date

trait LastCalls extends Prefs {

  val context: Context

  private class Call(val number: String, val date: Date) {

    def maxMilliseconds = minutesCount(context) * 60000

    def hasExpired =
      (new Date).getTime - date.getTime > maxMilliseconds

  }

  private var lastCalls: List[Call] = Nil

  private def cleanupList =
    lastCalls = lastCalls.filter(!_.hasExpired)

  private def rememberCall(c: Call) = {
    cleanupList
    lastCalls ::= c
  }

  def resetCalls =
    lastCalls = Nil

  // Return true if the maximum number of calls has been reached with
  // the current one. It does not record the call.
  def shouldBeSignaled(number: String): Boolean = {
    cleanupList
    lastCalls.count(_.number == number) >= callCount(context) - 1
  }

  def recordMissedCall(number: String) =
    rememberCall(new Call(number, new Date))

}
