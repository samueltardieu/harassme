package net.rfc1149.harassme

import _root_.android.content.Context
import _root_.android.media.AudioManager
import _root_.android.telephony.{PhoneStateListener, TelephonyManager}

class HarassListener (val context: Context)
extends PhoneStateListener with LastCalls {

  val audioManager =
    context.getSystemService(Context.AUDIO_SERVICE).asInstanceOf[AudioManager]

  val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)

  private var currentCall: Option[String] = None
  private var previousMode: Option[Int] = None
  private var previousVolume: Int = 0

  override def onCallStateChanged (state: Int, incomingNumber: String) {
    state match {
      case TelephonyManager.CALL_STATE_RINGING =>
	val ringerMode = audioManager.getRingerMode
	if (ringerMode != AudioManager.RINGER_MODE_NORMAL &&
	    incomingNumber != "") {
	      currentCall = Some(incomingNumber)
	      if (checkIncomingCall(incomingNumber))
		ringPhone(ringerMode)
	    }
      case TelephonyManager.CALL_STATE_IDLE =>
	maybeRecordCall
	maybeRestoreRingerMode
      case TelephonyManager.CALL_STATE_OFFHOOK =>
	maybeRestoreRingerMode
	// The call is getting answered, we can reset our whole list
	// since the owner is obviously aware of the phone.
	currentCall = None
	resetCalls
      case _ =>
    }
  }

  private def maybeRecordCall = {
    currentCall match {
      case Some(number) =>
	recordMissedCall(number)
      case None =>
    }
    currentCall = None
  }

  def ringPhone(ringerMode : Int) = {
    previousMode = Some(ringerMode)
    previousVolume =
      audioManager.getStreamVolume(AudioManager.RINGER_MODE_NORMAL)
    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL)
    val newVolume : Int = maxVolume * volume(context) / 100
    audioManager.setStreamVolume(AudioManager.STREAM_RING, newVolume, 0)
  }

  private def maybeRestoreRingerMode = {
    previousMode match {
      case Some(mode) =>
	audioManager.setRingerMode(mode)
	audioManager.setStreamVolume(AudioManager.STREAM_RING, previousVolume, 0)
      case None =>
    }
    previousMode = None
  }

}
