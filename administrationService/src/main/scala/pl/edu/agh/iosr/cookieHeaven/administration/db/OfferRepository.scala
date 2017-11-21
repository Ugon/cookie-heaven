package pl.edu.agh.iosr.cookieHeaven.administration.db

import org.springframework.data.mongodb.repository.MongoRepository

trait OfferRepository extends MongoRepository[Offer, String] {
  def findById(id: String): Offer
}
