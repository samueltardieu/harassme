package net.rfc1149.harassme

import _root_.android.app.Service
import _root_.android.content.{Context, Intent}
import _root_.android.telephony.{PhoneStateListener, TelephonyManager}

class HarassMeService extends Service {

  private lazy val telephonyManager =
    getSystemService(Context.TELEPHONY_SERVICE).asInstanceOf[TelephonyManager]

  private lazy val listener = new HarassMeListener(this)

  override def onBind(intent: Intent) = {
    // Do nothing as the service is not designed to be bound
    null
  }

  override def onCreate() {
    super.onCreate
    telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE)
  }

  override def onDestroy() {
    super.onDestroy
    telephonyManager.listen(listener, 0)
  }

}

object HarassMeService extends Prefs {

  private var serviceStarted : Boolean = false

  private def intent(context: Context) =
    new Intent(context, classOf[HarassMeService])

  // Start service if it is activated and has not been started yet.
  def startService(context: Context) = {
    if (serviceActivated(context) && !serviceStarted)
      context.startService(intent(context))
    serviceStarted = true
  }

  // Stop service if it has been started.
  def stopService(context: Context) = {
    if (serviceStarted)
      context.stopService(intent(context))
    serviceStarted = false
  }

  def serviceRunning = serviceStarted

}
