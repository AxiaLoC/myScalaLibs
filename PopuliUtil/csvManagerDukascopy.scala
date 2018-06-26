package PopuliUtil

import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter

class csvManagerDukascopy (inputFilePathName : String) extends csvManger (inputFilePathName : String) {

  def parseLineData(line: String): historyData ={
    val myList = parseLine(line)
    val myHistoryData = new historyData(LocalDateTime.parse(myList.head, DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm:ss.SSS")),
      myList(1).toDouble,myList(2).toDouble,myList(3).toDouble,myList(4).toDouble,myList(5).toDouble)
    myHistoryData

  }
}
