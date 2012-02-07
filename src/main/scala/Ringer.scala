package net.rfc1149.harassme
package listener

import android.content.Context
import android.media.{AudioManager, Ringtone, RingtoneManager}
import android.media.AudioManager.{RINGER_MODE_NORMAL, STREAM_ALARM, STREAM_RING}
import android.provider.Settings.System.DEFAULT_RINGTONE_URI

trait Ringer {

  val context: Context

  private val audioManager =
    context.getSystemService(Context.AUDIO_SERVICE).asInstanceOf[AudioManager]

  private var currentlyPlaying: Option[Ringtone] = None

  def isSilent =
    audioManager.getRingerMode != RINGER_MODE_NORMAL ||
    audioManager.getStreamVolume(STREAM_RING) == 0

  def ringPhone {
    if (isSilent) {
      val r = RingtoneManager.getRingtone(context, DEFAULT_RINGTONE_URI)
      r.setStreamType(STREAM_ALARM)
      r.play
      currentlyPlaying = Some(r)
    }
  }

  def unringPhone = {
    currentlyPlaying foreach (_.stop)
    currentlyPlaying = None
  }

}
