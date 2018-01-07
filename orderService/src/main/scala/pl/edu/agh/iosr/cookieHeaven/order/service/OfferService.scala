package pl.edu.agh.iosr.cookieHeaven.order.service

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import pl.edu.agh.iosr.cookieHeaven.order.db.ScalaObjectMapper

import scala.collection.JavaConverters._

@Service
class OfferService {

  @Autowired var restTemplate: RestTemplate = _

  val offerServiceUrl = "http://adminservice:8001"

  def get(offerId: String): String = {
    restTemplate.getForObject[String](s"$offerServiceUrl/offers/$offerId", classOf[String])
  }

  def listOffers(): Iterable[JsonNode] = {
    val response = restTemplate.getForObject[String](s"$offerServiceUrl/offers", classOf[String])
    val mapper = new ScalaObjectMapper
    val json = mapper.readTree(response)
    json.asScala
  }

}
