package net.rfc1149.harassme

import android.content.Context
import android.widget.Toast

trait Notification { self: Context =>

  def short_toast(strId: Int) =
    Toast.makeText(self, strId, Toast.LENGTH_SHORT).show

}
