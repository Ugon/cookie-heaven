package pl.edu.agh.iosr.cookieHeaven.administration.db

import com.avsystem.commons.jiop.BasicJavaInterop._
import com.mongodb.client.{MongoCollection, MongoDatabase}
import pl.edu.agh.iosr.cookieHeaven.domain.Offer

/**
  * @author Wojciech Pachuta.
  */
class OfferDao private(mongoCollection: MongoCollection[Offer]) {

}

object OfferDao {
  def create(mongoDatabase: MongoDatabase): OfferDao = new OfferDao(mongoDatabase.getCollection("Offer", classOf[Offer]))
}