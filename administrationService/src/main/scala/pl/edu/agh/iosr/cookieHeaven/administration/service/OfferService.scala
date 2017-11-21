package pl.edu.agh.iosr.cookieHeaven.administration.service

import java.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.agh.iosr.cookieHeaven.administration.db.{Offer, OfferRepository}

/**
  * @author Wojciech Pachuta
  * @author Jakub Kudzia
  */

@Service
class OfferService @Autowired()(offerRepository: OfferRepository) {

  def list(): util.List[Offer] = offerRepository.findAll()

  def get(id: String): Offer = offerRepository.findById(id)

  def add(offer: Offer): Offer = offerRepository.insert(offer)

  def remove(id: String): Unit = offerRepository.delete(id)


}
