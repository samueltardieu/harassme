package net.rfc1149.harassme

import _root_.android.content.Context
import _root_.android.media.AudioManager
import _root_.android.media.AudioManager._

trait Ringer extends Prefs {

  case class RingerMode (val mode: Int, val volume: Int) extends Ordered[RingerMode] {

    // RingerMode implements "audible" comparaison. If one mode is greater than the other,
    // it means that it has a better chance to be heard. The volume has no significance
    // when the mode is not the normal one.

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
    audioManager.getStreamMaxVolume(STREAM_RING)

  private var previousSettings: Option[RingerMode] = None

  def ringPhone = {
    val currentSettings = getSettings
    val wantedVolume = volume(context)
    val newSettings = if (wantedVolume == 0)
			new RingerMode(RINGER_MODE_VIBRATE, 0)
		      else
			new RingerMode(RINGER_MODE_NORMAL, maxVolume * volume(context) / 100)
    if (newSettings > currentSettings) {
      previousSettings = Some(currentSettings)
      installSettings(newSettings)
    }
  }

  def unringPhone = {
    previousSettings foreach installSettings
    previousSettings = None
  }

  def getSettings: RingerMode = {
    val mode = audioManager.getRingerMode
    RingerMode(mode, if (mode == RINGER_MODE_NORMAL) audioManager.getStreamVolume(STREAM_RING) else 0)
  }

  def installSettings(settings: RingerMode) = {
    audioManager.setRingerMode(settings.mode)
    if (settings.mode == RINGER_MODE_NORMAL)
      audioManager.setStreamVolume(STREAM_RING, settings.volume, 0)
  }

}
