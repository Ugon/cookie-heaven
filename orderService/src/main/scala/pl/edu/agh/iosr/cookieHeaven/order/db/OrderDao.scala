package pl.edu.agh.iosr.cookieHeaven.order.db

import com.avsystem.commons.jiop.BasicJavaInterop._
import com.mongodb.client.{MongoCollection, MongoDatabase}
import pl.edu.agh.iosr.cookieHeaven.domain.Order

/**
  * @author Wojciech Pachuta.
  */
class OrderDao private(mongoCollection: MongoCollection[Order]) {
  def insert(order: Order): Unit = mongoCollection.insertOne(order)
  def getAll: Iterable[Order] = mongoCollection.find().asScala
}

object OrderDao {
  def create(mongoDatabase: MongoDatabase): OrderDao = new OrderDao(mongoDatabase.getCollection("Order", classOf[Order]))
}