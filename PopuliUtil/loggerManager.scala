package myScalaLibs.PopuliUtil

import java.time.{LocalDateTime, ZoneOffset}

object loggerManager  {
  var gestoreFile = new gestoreInOut ("logger2.txt")
  def writeLine(myLine: String, append: Boolean = true): Unit = gestoreFile.writeOnFile(
    "[" + LocalDateTime.now(ZoneOffset.UTC).toString+
      " "+LocalDateTime.now().getHour+":"+LocalDateTime.now().getMinute+":"+LocalDateTime.now().getSecond+"]  "+
      myLine,append)
}
