package net.rfc1149.harassme
package listener

import android.content.Context
import java.util.Date

object LastCalls extends {

  private class Call(val number: String, val date: Date) {

    def hasExpired(expirationDate: Long) =
      date.getTime < expirationDate

  }

}

trait LastCalls extends Prefs {

  import LastCalls._

  private var lastCalls: List[Call] = Nil

  private def cleanupList = {
    val expirationDate = (new Date).getTime - minutesCount * 60000
    lastCalls = lastCalls.filter(!_.hasExpired(expirationDate))
  }

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
    lastCalls.count(_.number == number) >= callCount - 1
  }

  def recordMissedCall(number: String) =
    rememberCall(new Call(number, new Date))

}
