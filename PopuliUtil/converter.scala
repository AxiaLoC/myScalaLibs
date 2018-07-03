package myScalaLibs.PopuliUtil

import java.time.{LocalDateTime, ZoneId}
import java.util.Date

object converter {
  def c_LocalDateTime_to_Date(inData:LocalDateTime):Date = {
    new Date(inData.getYear-1900, inData.getMonthValue-1, inData.getDayOfMonth, inData.getHour, inData.getMinute, inData.getSecond)
  }

  def c_Date_to_LocalDateTime(inDate: Date): LocalDateTime = {
    LocalDateTime.ofInstant(inDate.toInstant, ZoneId.systemDefault)
  }
}
