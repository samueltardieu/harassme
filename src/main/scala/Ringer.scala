package net.rfc1149.harassme

import _root_.android.content.Context
import _root_.android.media.AudioManager

trait Ringer extends Prefs {

  val context: Context

  private val audioManager =
    context.getSystemService(Context.AUDIO_SERVICE).asInstanceOf[AudioManager]
  private val maxVolume =
    audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)

  private var previousMode: Option[Int] = None
  private var previousVolume: Int = 0

  def ringPhone(ringerMode : Int) = {
    previousMode = Some(ringerMode)
    previousVolume =
      audioManager.getStreamVolume(AudioManager.RINGER_MODE_NORMAL)
    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL)
    val newVolume : Int = maxVolume * volume(context) / 100
    audioManager.setStreamVolume(AudioManager.STREAM_RING, newVolume, 0)
  }

  def restoreRingerMode = {
    previousMode match {
      case Some(mode) =>
	audioManager.setRingerMode(mode)
	audioManager.setStreamVolume(AudioManager.STREAM_RING, previousVolume, 0)
      case None =>
    }
    previousMode = None
  }

  def getRingerMode: Int = audioManager.getRingerMode

}
