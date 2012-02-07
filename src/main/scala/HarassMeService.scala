package net.rfc1149.harassme
package service

import android.app.Service
import android.content.{Context, Intent}
import android.telephony.{PhoneStateListener, TelephonyManager}

import listener.HarassMeListener

class HarassMeService extends Service {

  private lazy val telephonyManager =
    getSystemService(Context.TELEPHONY_SERVICE).asInstanceOf[TelephonyManager]

  private lazy val listener = new HarassMeListener(this)

  override def onBind(intent: Intent) = {
    // Do nothing as the service is not designed to be bound
    null
  }

  override def onCreate() {
    HarassMeService.serviceStarted = true
    super.onCreate
    telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE)
  }

  override def onDestroy() {
    super.onDestroy
    telephonyManager.listen(listener, 0)
    HarassMeService.serviceStarted = false
  }

}

object HarassMeService {

  private var serviceStarted : Boolean = false

  private def intent(context: Context) =
    new Intent(context, classOf[HarassMeService])

  // Start service if it has not been started yet.
  def startService(context: Context) =
    context.startService(intent(context))

  // Stop service if it has been started.
  def stopService(context: Context) =
    context.stopService(intent(context))

  def serviceRunning = serviceStarted

}
