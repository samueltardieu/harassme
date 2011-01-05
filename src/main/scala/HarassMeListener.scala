package net.rfc1149.harassme

import _root_.android.content.Context
import _root_.android.media.AudioManager

class HarassMeListener (val context: Context)
extends CallStatus with LastCalls with Ringer {

  override def onAnswer(incomingNumber: String) =
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
