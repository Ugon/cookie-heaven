package pl.edu.agh.iosr.cookieHeaven.administration.web

import java.util

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._
import pl.edu.agh.iosr.cookieHeaven.administration.db.Offer
import pl.edu.agh.iosr.cookieHeaven.administration.service.{OfferService, OrderService}


/**
  * @author Wojciech Pachuta
  * @author Jakub Kudzia
  */

@RestController
@RequestMapping(Array("/offers"))
class OfferController @Autowired()(offerService: OfferService, orderService: OrderService) {

  @Value("${misc.message}")
  var message: String = _

  @GetMapping
  def listOffers: util.List[Offer] = offerService.list()

  @PostMapping
  def add(@RequestBody offer: Offer): Unit = offerService.add(offer)

  @GetMapping(Array("message"))
  def msg: String = message

  @GetMapping(Array("{id}"))
  def get(@PathVariable id: String): Offer = {
    val offer = offerService.get(id)
    if (offer == null)
      throw new OfferNotFoundException(s"Not found offer with id: $id")
    else
      offer
  }

  @DeleteMapping(Array("{id}"))
  def delete(@PathVariable id: String): Unit = {
    val orders = listOrdersForOffer(id)
    if (orders.isEmpty)
      offerService.remove(id)
    else
      throw new ExistingOrdersException(s"Orders associated with offer $id exist")
  }

  @GetMapping(Array("{id}/orders"))
  def listOrdersForOffer(@PathVariable id: String): Iterable[JsonNode] =
    orderService.listOrdersForOffer(id)


  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  def handleException(offerNotFoundException: OfferNotFoundException): String = {
    offerNotFoundException.getMessage
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  def handleException(existingOrdersException: ExistingOrdersException): String = {
    existingOrdersException.getMessage
  }

}

class OfferNotFoundException(message: String) extends RuntimeException

class ExistingOrdersException(message: String) extends RuntimeException

