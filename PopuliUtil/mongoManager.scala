package myScalaLibs.PopuliUtil
import java.util.Date

import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Sorts._

import scala.collection.mutable


object mongoManager {

  import org.mongodb.scala.bson.collection.immutable.Document
  import org.mongodb.scala.{Completed, MongoClient, MongoCollection, MongoDatabase, Observable, Observer}

  val mongoSrvPath = "mongodb+srv://AxiaLoC:Pegasus_1086@cluster0-zkyqq.mongodb.net/test?retryWrites=true"
  var selectedDB = "noDbSelected"
  var selectedCollection = "noCollectionSelected"

  private def connect(): MongoClient ={
    System.setProperty("org.mongodb.async.type", "netty")
    val client: MongoClient = MongoClient(mongoSrvPath)
    client
  }

  def selectDB(input:String){selectedDB = input}

  def selectCollection(input:String){selectedCollection = input}

  def selectDbCollection(inputDB:String, inputColl:String){selectedDB = inputDB; selectedCollection = inputColl}

  def insertBar (iData: historyData){

    val myClient = connect()
    val database: MongoDatabase = myClient.getDatabase(selectedDB)
    val collection: MongoCollection[Document] = database.getCollection(selectedCollection)
    val doc: Document = Document("_id" -> iData.data.toString,
      "open" -> iData.open,
      "high" -> iData.high,
      "low" -> iData.low,
      "close" -> iData.close,
      "volume" -> iData.volume
    )

    //collection.insertOne(doc)
    val observable: Observable[Completed] = collection.insertOne(doc)

    // Explictly subscribe:
    observable.subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit = println("Inserted")
      override def onError(e: Throwable): Unit = {
        println("Failed")
      }
      override def onComplete(): Unit = println("Completed")
    })

    Thread.sleep(5000)
    myClient.close()

  }

  def insertBar (iDataList: List[historyData]){

    val myClient = connect()
    val database: MongoDatabase = myClient.getDatabase(selectedDB)
    val collection: MongoCollection[Document] = database.getCollection(selectedCollection)

    var i = 0
    val documents = (i until iDataList.size) map { i: Int => Document(
      "_id" -> Document( "date" -> iDataList(i).datap, "timeframe" -> iDataList(i).tf),
      "open" -> iDataList(i).open,
      "high" -> iDataList(i).high,
      "low" -> iDataList(i).low,
      "close" -> iDataList(i).close,
      "volume" -> iDataList(i).volume) }

    val observable: Observable[Completed] = collection.insertMany(documents)

    // Explictly subscribe:
    observable.subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit = println("Inserted")
      override def onError(e: Throwable): Unit = println("Failed", e)
      override def onComplete(): Unit = println("Completed")
    })


    println("isertMany")



    Thread.sleep(5000)
    myClient.close()

  }

  def findBarEqual (nameColumn: String, value: Double) : mutable.MutableList[historyData] = {

    val myClient = connect()
    val database: MongoDatabase = myClient.getDatabase(selectedDB)
    val collection: MongoCollection[Document] = database.getCollection(selectedCollection)
    var list = scala.collection.mutable.MutableList[BsonDocument]()
    collection.find(equal(nameColumn, value)).subscribe(
      (doc: Document) => {
        list +=  BsonDocument(doc.toJson())
      },
      (e: Throwable) => println(s"There was an error: $e"),
      () => println("Completed!"))
    Thread.sleep(10000)
    myClient.close()
    mongoBarsToHistoryData(list)
  }

  def findBarEqual (nameColumn: String, value: String) : mutable.MutableList[historyData] = {

    val myClient = connect()
    val database: MongoDatabase = myClient.getDatabase(selectedDB)
    val collection: MongoCollection[Document] = database.getCollection(selectedCollection)
    var list = scala.collection.mutable.MutableList[BsonDocument]()
    val query = Document(nameColumn -> value)
    collection.find(query).subscribe(
      (doc: Document) => {
        list +=  BsonDocument(doc.toJson())
      },
      (e: Throwable) => println(s"There was an error: $e"),
      () => println("Completed!"))
    Thread.sleep(10000)
    myClient.close()
    mongoBarsToHistoryData(list)

  }

  def getPeriodBar(days: Int, startDate: Date = new Date()) : mutable.MutableList[historyData] = {

    val myClient = connect()
    val database: MongoDatabase = myClient.getDatabase(selectedDB)
    val collection: MongoCollection[Document] = database.getCollection(selectedCollection)
    var list = scala.collection.mutable.MutableList[BsonDocument]()
    val date2 = converter.c_LocalDateTime_to_Date(converter.c_Date_to_LocalDateTime(startDate).minusDays(days))
    collection.find(and(gte("_id.date", date2), lt("_id.date", startDate))).sort(descending("_id.date")).subscribe(
      (doc: Document) => {
        list +=  BsonDocument(doc.toJson())
      },
      (e: Throwable) => println(s"There was an error: $e"),
      () => println("Completed!"))
    Thread.sleep(10000)
    myClient.close()
    mongoBarsToHistoryData(list)

  }

  private def mongoBarsToHistoryData (inList: mutable.MutableList[BsonDocument]): mutable.MutableList[historyData] = {

    var outList = scala.collection.mutable.MutableList[historyData]()

    for(e <- inList){
      val ro2 = BsonDocument(e.get("_id").toString)
      val mydata = new Date(ro2.get("date").asDateTime().getValue)
      println("ro2 vale: " + mydata)

      val x = new historyData(
        converter.c_Date_to_LocalDateTime(new Date(BsonDocument(e.get("_id").toString).get("date").asDateTime().getValue)),
        e.get("open").asDouble().getValue,
        e.get("high").asDouble().getValue,
        e.get("low").asDouble().getValue,
        e.get("close").asDouble().getValue,
        e.get("volume").asDouble().getValue,
        BsonDocument(e.get("_id").toString).get("timeframe").asString().getValue
      )
      println("ro2 vale: " + mydata)
      outList +=  x
    }
    outList
  }

  def exampleFindBar (nameColumn: String, value: Double){

    val myClient = connect()
    val database: MongoDatabase = myClient.getDatabase("voxpopuli")
    val collection: MongoCollection[Document] = database.getCollection("bars")

    //    val q4 = "timestamp" : { $lt: new Date(), $gte: new Date(new Date().setDate(new Date().getDate()-1))}
    // {'_id.date':{$gte:ISODate("2018-06-18 00:00:00.000"), $lt:ISODate("2018-06-18 10:00:00.000")} }
    //val query3 = Document.parse("_id.date" -> {$gte:ISODate("2018-06-18 00:00:00.000"), $lt:ISODate("2018-06-18 10:00:00.000")})

    //    val q1 = Document( "_id.date" -> new Date(2018-1900, 5,18, 0,0,0))
    //    val q2 = Document( "_id.date" -> new Date(2018-1900, 5,18, 10,0,0))


    collection.find(equal(nameColumn, value)).subscribe((doc: Document) => {
      println (doc.toJson())

      //      val x = doc.get("_id")
      //      val ro = BsonDocument(doc.toJson())
      //      println("ro vale: ",ro.get("_id").toString)
      //      val ro2 = BsonDocument(ro.get("_id").toString)
      //      println("ro2 vale: ",ro2.get("date").asString().getValue)
      //      println(ro.get("open").asDouble())



    },
      (e: Throwable) => println(s"There was an error: $e"),
      () => println("Completed!"))

    Thread.sleep(10000)
    myClient.close()

  }

}

