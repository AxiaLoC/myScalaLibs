
package PopuliUtil

class gestoreInOut(inputFilePathName : String) {


  private var myFilePathName = inputFilePathName

  def initGestore(pathAndName: String): Unit =
  {
    myFilePathName = pathAndName
  }

  def clearFile(): Unit = {
    import java.io._
    val pw = new FileWriter(new File(myFilePathName), false)
    pw.write("")
    pw.close()
  }

  def writeOnFile(inputLine: String, pathAndName: String,  append: Boolean): Unit = {
    // PrintWriter
    import java.io._
    val pw = new FileWriter(new File(pathAndName), append)
    pw.write(inputLine + "\r\n")
    pw.close()
  }

  def writeOnFile(inputLine: String, append: Boolean = false): Unit = {
    // PrintWriter
    import java.io._
    val pw = new FileWriter(new File(myFilePathName), append)
    pw.write(inputLine + "\r\n")
    pw.close()
  }

  def readAllLine(): List[String] ={
    var  myList = List[String]()
    import scala.io.Source
    val filename = myFilePathName
    for (line <- Source.fromFile(filename).getLines) {
      myList = myList:::List(line)
    }
    myList
  }

  def readLine(lineNumber: Int = 0): String ={
    // lineNumber indicizzato partendo da 0
    val myList = readAllLine()
    myList(lineNumber)
  }

  def readLastLine(): String ={
    var appoString = ""
    val myList = readAllLine()
    for (e <- myList) {appoString = e}
    appoString
  }

  def countMyLines(): Int = {
    import scala.io.Source
    Source.fromFile(myFilePathName).getLines.size
  }


}
