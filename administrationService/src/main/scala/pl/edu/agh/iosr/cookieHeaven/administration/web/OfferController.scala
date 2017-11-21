package pl.edu.agh.iosr.cookieHeaven.administration.web

import java.util

import com.fasterxml.jackson.databind.JsonNode
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._
import pl.edu.agh.iosr.cookieHeaven.administration.db.{Offer, ScalaObjectMapper}
import pl.edu.agh.iosr.cookieHeaven.administration.service.OfferService

import scala.collection.JavaConverters._


/**
  * @author Wojciech Pachuta
  * @author Jakub Kudzia
  */

@RestController
@RequestMapping(Array("/offers"))
class OfferController(offerService: OfferService) {

  val orderServiceUrl = "http://localhost:8002" //fixme hardcoded port

  @GetMapping
  def listOffers: util.List[Offer] = offerService.list()

  @PostMapping
  def add(@RequestBody offer: Offer): Unit = offerService.add(offer)

  @GetMapping(Array("{id}"))
  def get(@PathVariable id: String): Offer = {
    val offer = offerService.get(id)
    if (offer == null)
      throw new OfferNotFoundException(s"Not found offer with id: $id")
    else
      offer
  }

  @DeleteMapping(Array("{id}"))
  //todo handle deletions of offers that had been ordered
  def delete(@PathVariable id: String): Unit = offerService.remove(id)

  @GetMapping(Array("/{id}/orders}"))
  def listOrdersForOffer(@PathVariable id: String): Iterable[JsonNode] = {
    val client = new DefaultHttpClient()
    val response = client.execute(new HttpGet(s"$orderServiceUrl/offers/$id"))
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

