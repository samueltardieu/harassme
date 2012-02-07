package net.rfc1149.harassme
package listener

import android.content.Context
import android.media.AudioManager

class HarassMeListener (val context: Context)
extends CallStatus with LastCalls with Ringer {

  override def onAnswer(incomingNumber: Option[String]) = {
    unringPhone
    resetCalls
  }

  override def onRing(incomingNumber: Option[String]) =
    if (incomingNumber.map(shouldBeSignaled(_)) getOrElse false)
      ringPhone

  override def onMissedCall(incomingNumber: Option[String]) = {
    unringPhone
    incomingNumber.foreach(recordMissedCall(_))
  }

}
