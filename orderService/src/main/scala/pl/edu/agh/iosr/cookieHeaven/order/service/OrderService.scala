package pl.edu.agh.iosr.cookieHeaven.order.service

import java.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.agh.iosr.cookieHeaven.order.db.{Order, OrderRepository}

/**
  * @author Wojciech Pachuta.
  */

@Service
class OrderService @Autowired()(orderRepository: OrderRepository) {

  def insert(order: Order): Order = orderRepository.insert(order)

  def list: util.List[Order] = orderRepository.findAll()

  def findByOfferId(id: String): util.List[Order] = orderRepository.findByOfferId(id)

  def remove(id: String): Unit = orderRepository.delete(id)

}
