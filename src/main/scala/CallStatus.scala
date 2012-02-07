package net.rfc1149.harassme
package listener

import android.telephony.{PhoneStateListener, TelephonyManager}

trait CallStatus extends PhoneStateListener {

  private var previousState = TelephonyManager.CALL_STATE_IDLE
  private var currentNumber = ""
  private var incomingCall = true

  protected def onRing(incomingNumber: Option[String]) = {}

  protected def onAnswer(incomingNumber: Option[String]) = {}

  protected def onIncomingHangUp(incomingNumber: Option[String]) = {}

  protected def onOutgoingHangUp(outgoingNumber: Option[String]) = {}

  protected def onOutgoingCall(outgoingNumber: Option[String]) = {}

  protected def onMissedCall(incomingNumber: Option[String]) = {}

  override def onCallStateChanged(state: Int, phoneNumber: String) {
    import android.telephony.TelephonyManager._
    val number = if (phoneNumber == null || phoneNumber.isEmpty) None else Some(phoneNumber)
    (previousState, state) match {
      case (CALL_STATE_IDLE, CALL_STATE_RINGING) =>
	onRing(number)
	incomingCall = true
      case (CALL_STATE_IDLE, CALL_STATE_OFFHOOK) =>
	onOutgoingCall(number)
	incomingCall = false
      case (CALL_STATE_RINGING, CALL_STATE_OFFHOOK) =>
	onAnswer(number)
      case (CALL_STATE_RINGING, CALL_STATE_IDLE) =>
	onMissedCall(number)
      case (CALL_STATE_OFFHOOK, CALL_STATE_IDLE) =>
	if (incomingCall)
	  onIncomingHangUp(number)
	else
	  onOutgoingHangUp(number)
      case _ =>
    }
    currentNumber = phoneNumber
    previousState = state
  }

}
