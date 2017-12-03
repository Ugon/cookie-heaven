package pl.edu.agh.iosr.cookieHeaven.administration.service

import com.fasterxml.jackson.databind.JsonNode
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.springframework.stereotype.Service
import pl.edu.agh.iosr.cookieHeaven.administration.db.ScalaObjectMapper

import scala.collection.JavaConverters._

@Service
class OrderService {

  val orderServiceUrl = "http://localhost:8002" //fixme hardcoded port
  val client = new DefaultHttpClient()

  def listOrdersForOffer(id: String): Iterable[JsonNode] = {
    val response = client.execute(new HttpGet(s"$orderServiceUrl/orders/offers/$id"))
    println("DUIPA", response)
    val mapper = new ScalaObjectMapper
    val json = mapper.readTree(response.getEntity.getContent)
    json.asScala
  }

}
