package myScalaLibs.PopuliUtil

import java.time.LocalDateTime

class historyData (idata:LocalDateTime, iopen:Double, ihigh:Double, ilow:Double, iclose:Double, ivolume:Double, itf:String) {

  var data:LocalDateTime = idata
  var open:Double = iopen
  var high:Double = ihigh
  var low:Double = ilow
  var close:Double = iclose
  var volume:Double = ivolume
  var tf:String = itf
}
