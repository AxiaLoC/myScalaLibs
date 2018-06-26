package PopuliUtil

import java.time.{LocalDate, LocalDateTime}

class historyData (idata:LocalDateTime, iopen:Double, ihigh:Double, ilow:Double, iclose:Double, ivolume:Double) {

  var data = idata
  var open = iopen
  var high = ihigh
  var low = ilow
  var close = iclose
  var volume= ivolume

}
