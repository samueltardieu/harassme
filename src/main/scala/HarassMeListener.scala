package net.rfc1149.harassme
package listener

import android.content.Context
import android.media.AudioManager

class HarassMeListener (val context: Context)
extends CallStatus with LastCalls with Ringer {

  override def onAnswer(incomingNumber: String) =
    unringPhone
    resetCalls

  override def onRing(incomingNumber: String) = {
    if (incomingNumber != "" &&
	shouldBeSignaled(incomingNumber))
      ringPhone
  }

  override def onMissedCall(incomingNumber: String) = {
    unringPhone
    if (incomingNumber != "")
      recordMissedCall(incomingNumber)
  }

}
