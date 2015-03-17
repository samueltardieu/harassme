package net.rfc1149.harassme
package ringer

import android.app.Service
import android.content.Intent
import android.media.AudioManager.STREAM_ALARM
import android.media.{Ringtone, RingtoneManager}
import android.provider.Settings.System.DEFAULT_RINGTONE_URI

class RingerService extends Service {

  private[this] var currentlyPlaying: Option[Ringtone] = None

  private[this] def ringPhone() = {
    if (currentlyPlaying.isEmpty) {
      val r = RingtoneManager.getRingtone(this, DEFAULT_RINGTONE_URI)
      r.setStreamType(STREAM_ALARM)
      r.play()
      currentlyPlaying = Some(r)
    }
  }

  private[this] def unringPhone() = {
    currentlyPlaying foreach (_.stop)
    currentlyPlaying = None
  }

  override def onStartCommand(intent: Intent, flags: Int, startId: Int) = {
    ringPhone()
    Service.START_STICKY
  }

  override def onDestroy() = unringPhone()

  override def onBind(intent: Intent) = null

}
