package net.rfc1149.harassme

import _root_.android.content.Context
import _root_.android.media.AudioManager

class HarassMeListener (val context: Context)
extends CallStatus with LastCalls with Ringer {

  override def onAnswer(incomingNumber: String) =
    resetCalls

  override def onRing(incomingNumber: String) = {
    val ringerMode = getRingerMode
    if (ringerMode != AudioManager.RINGER_MODE_NORMAL &&
	incomingNumber != "" &&
	shouldBeSignaled(incomingNumber))
      ringPhone(ringerMode)
  }

  override def onMissedCall(incomingNumber: String) = {
    restoreRingerMode
    if (incomingNumber != "")
      recordMissedCall(incomingNumber)
  }

}
