package PopuliUtil

class historyData {

  val DATE_POS = 0
  val OPEN_POS = 1
  val HIGH_POS = 2
  val LOW_POS = 3
  val CLOSE_POS = 4
  val VOLUME_POS = 5

  var  myDataList = List[List[String]]()

  def insertOneRecord(inputListData: List[String]): Unit = {
    var appoList = List[String]()
    appoList = appoList:::List(inputListData(DATE_POS))
    appoList = appoList:::List(inputListData(OPEN_POS))
    appoList = appoList:::List(inputListData(HIGH_POS))
    appoList = appoList:::List(inputListData(LOW_POS))
    appoList = appoList:::List(inputListData(CLOSE_POS))
    appoList = appoList:::List(inputListData(VOLUME_POS))
    myDataList = myDataList:::List(appoList)
  }
}
