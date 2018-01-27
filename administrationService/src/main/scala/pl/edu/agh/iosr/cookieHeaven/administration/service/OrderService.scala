package pl.edu.agh.iosr.cookieHeaven.administration.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpEntity, HttpHeaders, HttpMethod}
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class OrderService {

  val orderServiceUrl = "http://orderservice:8002"
//  val orderServiceUrl = "http://localhost:8002"

  @Autowired var restTemplate: RestTemplate = _

  def listOrdersForOffer(id: String): String = {
    val response = restTemplate.exchange(s"$orderServiceUrl/login?login=ugon&pass=ala123", HttpMethod.POST, null, classOf[String])

    val headers = new HttpHeaders()
    headers.set("Authorization", response.getHeaders.get("Authorization").get(0))
    val entity = new HttpEntity[String](headers)

    restTemplate.exchange(s"$orderServiceUrl/orders/offers/$id", HttpMethod.GET, entity, classOf[String]).getBody
  }

}
