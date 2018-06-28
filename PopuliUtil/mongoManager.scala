package myScalaLibs.PopuliUtil

object mongoManager {

  import org.mongodb.scala.bson.collection.immutable.Document
  import org.mongodb.scala.{Completed, MongoClient, MongoCollection, MongoDatabase, Observable, Observer}

  private def connect(): MongoClient ={
    val uri: String = "mongodb://AxiaLoC:Pegasus_1086@cluster0-shard-00-00-zkyqq.mongodb.net:27017,cluster0-shard-00-01-zkyqq.mongodb.net:27017,cluster0-shard-00-02-zkyqq.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true"
    val uri2: String ="mongodb+srv://AxiaLoC:Pegasus_1086@cluster0-zkyqq.mongodb.net/test?retryWrites=true"
    System.setProperty("org.mongodb.async.type", "netty")
    val client: MongoClient = MongoClient(uri2)
    client
  }

  def insertBar (iData: historyData){

    val myClient = connect()
    val database: MongoDatabase = myClient.getDatabase("voxpopuli")
    val collection: MongoCollection[Document] = database.getCollection("bars")
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
      override def onError(e: Throwable): Unit = println("Failed")
      override def onComplete(): Unit = println("Completed")
    })

    Thread.sleep(5000)
    myClient.close()

  }


  def insertBar (iDataList: List[historyData]){

    val myClient = connect()
    val database: MongoDatabase = myClient.getDatabase("voxpopuli")
    val collection: MongoCollection[Document] = database.getCollection("bars")
    var i = 0

    val documents = (i until iDataList.size) map { i: Int => Document(
      "_id" -> Document( "date" -> iDataList(i).data.toString, "timeframe" -> iDataList(i).tf),
      "open" -> iDataList(i).open,
      "high" -> iDataList(i).high,
      "low" -> iDataList(i).low,
      "close" -> iDataList(i).close,
      "volume" -> iDataList(i).volume) }

    val observable: Observable[Completed] = collection.insertMany(documents)

    // Explictly subscribe:
    observable.subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit = println("Inserted")
      override def onError(e: Throwable): Unit = println("Failed")
      override def onComplete(): Unit = println("Completed")
    })

    Thread.sleep(5000)
    myClient.close()

  }

}
