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

object HarassMeService {

  private var serviceStarted : Boolean = false

  private def intent(context: Context) =
    new Intent(context, classOf[HarassMeService])

  def startService(context: Context) = {
    context.startService(intent(context))
    serviceStarted = true
  }

  def stopService(context: Context) = {
    context.stopService(intent(context))
    serviceStarted = false
  }

  def serviceRunning = serviceStarted

}
