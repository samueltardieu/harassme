package net.rfc1149.harassme
package ringer

import android.content.{Context, Intent}
import android.media.AudioManager

import AudioManager.{RINGER_MODE_NORMAL, STREAM_RING}

class Ringer(context: Context) {

  private val audioManager =
    context.getSystemService(Context.AUDIO_SERVICE).asInstanceOf[AudioManager]

  private def isSilent =
    audioManager.getRingerMode != RINGER_MODE_NORMAL ||
    audioManager.getStreamVolume(STREAM_RING) == 0

  private def intent = new Intent(context, classOf[RingerService])

  def ringPhone =
    if (isSilent)
      context.startService(intent)

  def unringPhone =
    context.stopService(intent)

}
