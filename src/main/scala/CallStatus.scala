package net.rfc1149.harassme

import android.telephony.{PhoneStateListener, TelephonyManager}

class CallStatus extends PhoneStateListener {

  private var previousState = TelephonyManager.CALL_STATE_IDLE
  private var currentNumber = ""
  private var incomingCall = true

  protected def onRing(incomingNumber: String) = {}

  protected def onAnswer(incomingNumber: String) = {}

  protected def onIncomingHangUp(incomingNumber: String) = {}

  protected def onOutgoingHangUp(outgoingNumber: String) = {}

  protected def onOutgoingCall(outgoingNumber: String) = {}

  protected def onMissedCall(incomingNumber: String) = {}

  override def onCallStateChanged(state: Int, phoneNumber: String) {
    import android.telephony.TelephonyManager._
    (previousState, state) match {
      case (CALL_STATE_IDLE, CALL_STATE_RINGING) =>
	onRing(phoneNumber)
	incomingCall = true
      case (CALL_STATE_IDLE, CALL_STATE_OFFHOOK) =>
	onOutgoingCall(phoneNumber)
	incomingCall = false
      case (CALL_STATE_RINGING, CALL_STATE_OFFHOOK) =>
	onAnswer(currentNumber)
      case (CALL_STATE_RINGING, CALL_STATE_IDLE) =>
	onMissedCall(currentNumber)
      case (CALL_STATE_OFFHOOK, CALL_STATE_IDLE) =>
	if (incomingCall)
	  onIncomingHangUp(currentNumber)
	else
	  onOutgoingHangUp(currentNumber)
      case _ =>
    }
    currentNumber = phoneNumber
    previousState = state
  }

}
