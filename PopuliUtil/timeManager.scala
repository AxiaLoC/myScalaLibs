package myScalaLibs.PopuliUtil

object timeManager {

  import java.time.{LocalDateTime, ZoneId}

  def convertDataToUTC(idata:LocalDateTime, currentFormat:ZoneId):LocalDateTime={
    val newZone = ZoneId.of("UTC")
    val myDate = idata.atZone(currentFormat).withZoneSameInstant(newZone).toLocalDateTime
    myDate
  }

  def convertData(idata:LocalDateTime, currentFormat:ZoneId, newFormat:ZoneId):LocalDateTime={
    val myDate = idata.atZone(currentFormat).withZoneSameInstant(newFormat).toLocalDateTime
    myDate
  }
}
