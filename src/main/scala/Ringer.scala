package net.rfc1149.harassme

import _root_.android.content.Context
import _root_.android.media.AudioManager

trait Ringer extends Prefs {

  protected class RingerMode (val mode: Int, val volume: Int) extends Ordered[RingerMode] {

    // RingerMode implements "audible" comparaison. If one mode is greater than the other,
    // it means that it has a better chance to be heard.

    import AudioManager._

    def compare(other: RingerMode) =
      if (mode == other.mode) {
	if (mode == RINGER_MODE_NORMAL)
	  volume.compare(other.volume)
	else
	  0
      } else if (mode == RINGER_MODE_NORMAL)
	1
      else if (other.mode == RINGER_MODE_NORMAL)
	-1
      else if (mode == RINGER_MODE_VIBRATE)
	1
      else
	-1
  }

  val context: Context

  private val audioManager =
    context.getSystemService(Context.AUDIO_SERVICE).asInstanceOf[AudioManager]
  private val maxVolume =
    audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)

  private var previousSettings: Option[RingerMode] = None

  def ringPhone = {
    val currentSettings = getSettings
    val newSettings = new RingerMode(AudioManager.RINGER_MODE_NORMAL, maxVolume * volume(context) / 100)
    if (newSettings > currentSettings) {
      previousSettings = Some(currentSettings)
      installSettings(newSettings)
    }
  }

  def restoreRingerMode = {
    previousSettings foreach installSettings
    previousSettings = None
  }

  def getSettings: RingerMode =
    new RingerMode(audioManager.getRingerMode, audioManager.getStreamVolume(AudioManager.STREAM_RING))

  def installSettings(settings: RingerMode) = {
    audioManager.setRingerMode(settings.mode)
    audioManager.setStreamVolume(AudioManager.STREAM_RING, settings.volume, 0)
  }

}
