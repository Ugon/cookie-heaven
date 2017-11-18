package pl.edu.agh.iosr.cookieHeaven.order.web

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._
import pl.edu.agh.iosr.cookieHeaven.domain.Order
import pl.edu.agh.iosr.cookieHeaven.order.service.OrderService

/**
  * @author Wojciech Pachuta.
  */
@RestController
@RequestMapping(Array("/orders"))
class OrderController @Autowired() (orderService: OrderService) {

  @RequestMapping
  def getAllHotels: Array[Order] = orderService.getAll.toArray

  @RequestMapping(method = Array(RequestMethod.POST))
  def insert(@RequestParam name: String): Unit = orderService.insert(Order(ObjectId.get(), name))

}