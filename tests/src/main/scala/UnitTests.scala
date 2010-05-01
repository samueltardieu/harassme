package net.rfc1149.harassme.tests 

import junit.framework.Assert._
import _root_.android.test.AndroidTestCase

class UnitTests extends AndroidTestCase {
  def testPackageIsCorrect {      
    assertEquals("net.rfc1149.harassme", getContext.getPackageName)
  }
}