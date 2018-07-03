package myScalaLibs.PopuliUtil

import java.time.{LocalDateTime, ZoneId}
import java.time.format.DateTimeFormatter

class csvManagerBuffer (inputFilePathName : String) extends csvManger (inputFilePathName : String){

  def parseLineData(line: String): historyData ={
    val myList = parseLine(line)
    val myHistoryData = new historyData(LocalDateTime.parse(myList.head),
      myList(1).toDouble,myList(2).toDouble,myList(3).toDouble,myList(4).toDouble,myList(5).toDouble, myList(6).toString)
    myHistoryData
  }

  def parseLineData(nline: Int): historyData ={
    val line = readLine(nline)
    val myList = parseLine(line)
    val myHistoryData = new historyData(
      timeManager.convertDataToUTC(LocalDateTime.parse(myList.head), ZoneId.of("UTC")),
      myList(1).toDouble,myList(2).toDouble,myList(3).toDouble,myList(4).toDouble,myList(5).toDouble, myList(6).toString)
    myHistoryData
  }

}
