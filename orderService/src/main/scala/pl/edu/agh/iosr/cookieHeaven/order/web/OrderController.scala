package pl.edu.agh.iosr.cookieHeaven.order.web

import java.util

import com.fasterxml.jackson.databind.JsonNode
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._
import pl.edu.agh.iosr.cookieHeaven.order.db.{Order, ScalaObjectMapper}
import pl.edu.agh.iosr.cookieHeaven.order.service.OrderService

import scala.collection.JavaConverters._


/**
  * @author Wojciech Pachuta.
  */
@RestController
@RequestMapping(Array("/orders"))
class OrderController @Autowired()(orderService: OrderService) {

  val offerServiceUrl = "http://localhost:8001" //fixme hardcoded port

  @GetMapping
  def list: util.List[Order] = orderService.list

  @PostMapping
  def insert(@RequestBody order: Order): Order = {
    val client = new DefaultHttpClient()
    val response = client.execute(new HttpGet(s"$offerServiceUrl/offers/${order.offerId}"))
    client.getConnectionManager.shutdown()
    if (response.getStatusLine.getStatusCode == 404)
      throw new OfferNotFoundException(s"Not found offer with id: ${order.offerId}")
    val mapper = new ScalaObjectMapper
    orderService.insert(order)
  }

  @GetMapping(Array("/offers/{id}"))
  def list(@PathVariable id: String): util.List[Order] = {
    orderService.findByOfferId(id)
  }

  //todo this method should be moved to gateway service
  @GetMapping(Array("/offers"))
  def listOffers(): Iterable[JsonNode] = {
    val client = new DefaultHttpClient()
    val response = client.execute(new HttpGet(s"$offerServiceUrl/offers"))
    val mapper = new ScalaObjectMapper
    val json = mapper.readTree(response.getEntity.getContent)
    client.getConnectionManager.shutdown()
    json.asScala
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  def handleException(offerNotFoundException: OfferNotFoundException): String = {
    offerNotFoundException.getMessage
  }

}

class OfferNotFoundException(message: String) extends RuntimeException
