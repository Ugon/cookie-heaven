package pl.edu.agh.iosr.cookieHeaven.order.service

import com.fasterxml.jackson.databind.JsonNode
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.springframework.stereotype.Service
import pl.edu.agh.iosr.cookieHeaven.order.db.ScalaObjectMapper

import scala.collection.JavaConverters._

@Service
class OfferService {

  val offerServiceUrl = "http://localhost:8001" //fixme hardcoded port

  def get(offerId: String): HttpResponse = {
    val client = new DefaultHttpClient()
    val response = client.execute(new HttpGet(s"$offerServiceUrl/offers/$offerId"))
    client.getConnectionManager.shutdown()
    response
  }

  def listOffers(): Iterable[JsonNode] = {
    val client = new DefaultHttpClient()
    val response = client.execute(new HttpGet(s"$offerServiceUrl/offers"))
    val mapper = new ScalaObjectMapper
    val json = mapper.readTree(response.getEntity.getContent)
    client.getConnectionManager.shutdown()
    json.asScala
  }

}
