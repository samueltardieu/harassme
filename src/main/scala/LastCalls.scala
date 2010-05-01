package net.rfc1149.harassme

import _root_.android.content.Context
import java.util.Date

trait LastCalls extends Prefs {

  val context: Context

  private class Call(val number: String, val date: Date) {

    def maxMilliseconds = minutesCount(context) * 60000

    def expired =
      (new Date).getTime - date.getTime > maxMilliseconds

  }

  private var lastCalls: List[Call] = List()

  private def cleanupList = {
    lastCalls = lastCalls.filter(!_.expired)
  }

  private def rememberCall(c: Call) = {
    cleanupList
    lastCalls ::= c
  }

  def resetCalls = {
    lastCalls = List()
  }

  // Return true if the maximum number of calls has been reached with
  // the current one. It does not record the call.
  def checkIncomingCall(number: String): Boolean = {
    cleanupList
    lastCalls.count(_.number == number) >= callCount(context) - 1
  }

  def recordMissedCall(number: String) =
    rememberCall(new Call(number, new Date))

}
