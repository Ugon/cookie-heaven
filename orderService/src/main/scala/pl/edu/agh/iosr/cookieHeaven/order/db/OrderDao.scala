package pl.edu.agh.iosr.cookieHeaven.order.db

import com.avsystem.commons.jiop.BasicJavaInterop._
import com.mongodb.client.{MongoCollection, MongoDatabase}
import org.bson.codecs.{Codec, DecoderContext, EncoderContext}
import pl.edu.agh.iosr.cookieHeaven.domain.Order
import com.avsystem.commons.SharedExtensions._
import org.bson.{BsonReader, BsonWriter}
import org.bson.codecs.configuration.{CodecRegistries, CodecRegistry}

/**
  * @author Wojciech Pachuta.
  */
class OrderDao private(mongoCollection: MongoCollection[Order]) {
  def insert(order: Order): Unit = mongoCollection.insertOne(order)

  def getAll: Iterable[Order] = mongoCollection.find().asScala
}

object OrderDao {
  def create(mongoDatabase: MongoDatabase): OrderDao = {
    val collection = mongoDatabase.getCollection("Order", classOf[Order])

    val codecCollection = collection.withCodecRegistry(CodecRegistries.fromRegistries(
      collection.getCodecRegistry, CodecRegistries.fromCodecs(OrderCodec)
    ))

    new OrderDao(codecCollection)
  }
}

object OrderCodec extends Codec[Order] {
  override def getEncoderClass: Class[Order] = classOf[Order]

  override def encode(bsonWriter: BsonWriter, t: Order, encoderContext: EncoderContext): Unit = {
    bsonWriter.writeStartDocument()
    bsonWriter.writeString("name", t.name)
    bsonWriter.writeEndDocument()

  }
  override def decode(bsonReader: BsonReader, decoderContext: DecoderContext): Order = {
    bsonReader.readStartDocument()
    val order = Order(
      bsonReader.readObjectId("_id"),
      bsonReader.readString("name")
    )
    bsonReader.readEndDocument()

    order
  }

}