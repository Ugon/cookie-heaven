package pl.edu.agh.iosr.cookieHeaven.administration.db

import org.springframework.data.mongodb.core.mapping.Document

/**
  * @author Wojciech Pachuta.
  */
@Document
case class Offer(id: String, name: String, price: Double) {


  override def canEqual(that: Any): Boolean = that.isInstanceOf[Offer]

  override def equals(that: Any): Boolean = {
    that match {
      case otherOffer: Offer =>
        canEqual(otherOffer) && id.equals(otherOffer.id) &&
          name.equals(otherOffer.name) && (price == otherOffer.price)
      case _ =>
        false
    }
  }

}
