package PopuliUtil

class csvManger(inputFilePathName : String)  extends gestoreInOut(inputFilePathName : String) {

  def readValue(row: Int, column:Int): String ={
    parseLine(readLine(row))(column)
  }

  def parseLine(line: String): List[String] = {
    line.split(",").map(_.trim).toList
  }

  def readValueFromLine(inputLine: String, column:Int): String ={
    parseLine(inputLine)(column)
  }

  def writeLine(myList: List[String], append: Boolean = false): Unit = {

    var myLine = ""
    for (n <- 0.until(myList.length)) {
      myLine += myList(n)
      if (n < myList.length - 1) myLine += ','
    }

    writeOnFile(myLine,append)
  }


}
