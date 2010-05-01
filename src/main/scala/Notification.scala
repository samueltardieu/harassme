package net.rfc1149.harassme

import _root_.android.content.Context
import _root_.android.widget.Toast

trait Notification { self: Context =>

  def short_toast(strId: Int) =
    Toast.makeText(self, strId, Toast.LENGTH_SHORT).show

}
