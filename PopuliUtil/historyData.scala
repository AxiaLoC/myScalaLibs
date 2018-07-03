package myScalaLibs.PopuliUtil

import java.time.LocalDateTime
import java.util.Date

class historyData (idata:LocalDateTime, iopen:Double, ihigh:Double, ilow:Double, iclose:Double, ivolume:Double, itf:String) {

  var data:LocalDateTime = idata
  var open:Double = iopen
  var high:Double = ihigh
  var low:Double = ilow
  var close:Double = iclose
  var volume:Double = ivolume
  var tf:String = itf
  var datap:Date = converter.c_LocalDateTime_to_Date(data)

  import java.time.ZoneOffset
  def printAll(): String = {data.atZone(ZoneOffset.UTC).toString+" "+open+" "+high+" "+low+" "+close+" "+volume+" "+tf}
  def printAsCsv(): String = {data.toString+","+open+","+high+","+low+","+close+","+volume+","+tf}

}
