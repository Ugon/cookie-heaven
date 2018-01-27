package pl.edu.agh.iosr.cookieHeaven.order.service

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpEntity, HttpHeaders, HttpMethod}
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import pl.edu.agh.iosr.cookieHeaven.order.db.ScalaObjectMapper

import scala.collection.JavaConverters._

@Service
class OfferService {

  @Autowired var restTemplate: RestTemplate = _

  val offerServiceUrl = "http://adminservice:8001"
//  val offerServiceUrl = "http://localhost:8001"

  def get(offerId: String): String = {
    val response = restTemplate.exchange(s"$offerServiceUrl/login?login=ugon&pass=ala123", HttpMethod.POST, null, classOf[String])

    val headers = new HttpHeaders()
    headers.set("Authorization", response.getHeaders.get("Authorization").get(0))
    val entity = new HttpEntity[String](headers)

    restTemplate.exchange(s"$offerServiceUrl/offers/$offerId", HttpMethod.GET, entity, classOf[String]).getBody
  }

  def listOffers(): Iterable[JsonNode] = {
    val response = restTemplate.exchange(s"$offerServiceUrl/login?login=ugon&pass=ala123", HttpMethod.POST, null, classOf[String])

    val headers = new HttpHeaders()
    headers.set("Authorization", response.getHeaders.get("Authorization").get(0))
    val entity = new HttpEntity[String](headers)

    val r = restTemplate.exchange(s"$offerServiceUrl/offers", HttpMethod.GET, entity, classOf[String]).getBody

//    val response = restTemplate.getForObject[String](s"$offerServiceUrl/offers", classOf[String])
    val mapper = new ScalaObjectMapper
    val json = mapper.readTree(r)
    json.asScala
  }

}
