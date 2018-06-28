package myScalaLibs.PopuliUtil



class csvManagerDukascopy (inputFilePathName : String) extends csvManger (inputFilePathName : String) {

  import java.time.{LocalDateTime, ZoneId}
  import java.time.format.DateTimeFormatter

  def parseLineData(line: String): historyData ={
    val myList = parseLine(line)
    val myHistoryData = new historyData(LocalDateTime.parse(myList.head, DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm:ss.SSS")),
      myList(1).toDouble,myList(2).toDouble,myList(3).toDouble,myList(4).toDouble,myList(5).toDouble, "H1")
    myHistoryData
  }

  def parseLineData(nline: Int): historyData ={
    val line = readLine(nline)
    val myList = parseLine(line)
    val myHistoryData = new historyData(
      timeManager.convertDataToUTC(LocalDateTime.parse(myList.head, DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm:ss.SSS")), ZoneId.of("UTC")),
      myList(1).toDouble,myList(2).toDouble,myList(3).toDouble,myList(4).toDouble,myList(5).toDouble, "H1")
    myHistoryData
  }

}
