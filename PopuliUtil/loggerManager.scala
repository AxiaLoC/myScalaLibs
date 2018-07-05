package myScalaLibs.PopuliUtil

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneOffset}

object loggerManager  {
  var gestoreFile = new gestoreInOut ("logger2.txt")
  def writeLine(myLine: String, append: Boolean = true): Unit = {

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss")

    gestoreFile.writeOnFile(
      "[" + LocalDateTime.now(ZoneOffset.UTC).format(formatter)+
        " "+LocalDateTime.now().format(formatter2)+"]  "+
        myLine,append)
  }
}
