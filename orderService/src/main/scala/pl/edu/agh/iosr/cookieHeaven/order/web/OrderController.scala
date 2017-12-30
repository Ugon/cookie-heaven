package pl.edu.agh.iosr.cookieHeaven.order.web

import java.util

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._
import pl.edu.agh.iosr.cookieHeaven.order.db.{Order, ScalaObjectMapper}
import pl.edu.agh.iosr.cookieHeaven.order.service.{OfferService, OrderService}


/**
  * @author Wojciech Pachuta.
  */
@RestController
@RequestMapping(Array("/orders"))
class OrderController @Autowired()(orderService: OrderService, offerService: OfferService) {

  @Value("${misc.message}")
  var message: String = _

  @GetMapping(Array("message"))
  def msg: String = message

  @GetMapping
  def list: util.List[Order] = orderService.list

  @PostMapping
  def insert(@RequestBody order: Order): Order = {
    val response = offerService.get(order.offerId)
    if (response.getStatusLine.getStatusCode == 404)
      throw new OfferNotFoundException(s"Not found offer with id: ${order.offerId}")
    val mapper = new ScalaObjectMapper
    orderService.insert(order)
  }

  @GetMapping(Array("/offers/{id}"))
  def list(@PathVariable id: String): util.List[Order] = {
    orderService.findByOfferId(id)
  }

  @GetMapping(Array("/offers"))
  def listOffers(): Iterable[JsonNode] = offerService.listOffers()

  @DeleteMapping(Array("{id}"))
  def delete(@PathVariable id: String): Unit = orderService.remove(id)

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  def handleException(offerNotFoundException: OfferNotFoundException): String = {
    offerNotFoundException.getMessage
  }

}

class OfferNotFoundException(message: String) extends RuntimeException
