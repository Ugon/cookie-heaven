package pl.edu.agh.iosr.cookieHeaven.administration.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class OrderService {

  val orderServiceUrl = "http://orderservice:8002"

  @Autowired var restTemplate: RestTemplate = _

  def listOrdersForOffer(id: String): String =
    restTemplate.getForObject[String](s"$orderServiceUrl/orders/offers/$id", classOf[String])

}
