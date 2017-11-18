package pl.edu.agh.iosr.cookieHeaven.administration.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._
import pl.edu.agh.iosr.cookieHeaven.administration.service.OfferService
import pl.edu.agh.iosr.cookieHeaven.domain.{Offer, Order}

/**
  * @author Wojciech Pachuta.
  */
@RestController
@RequestMapping(Array("/orders"))
class OfferController @Autowired()(offerService: OfferService) {

//  @RequestMapping
//  def getAllOffers: Array[Offer] = ???
//
//  @RequestMapping(method = Array(RequestMethod.POST))
//  def insert(@RequestBody offer: Offer): Unit = ???

}