package pl.edu.agh.iosr.cookieHeaven.order.service

import org.springframework.stereotype.Service
import pl.edu.agh.iosr.cookieHeaven.domain.Order
import pl.edu.agh.iosr.cookieHeaven.order.db.OrderDao

/**
  * @author Wojciech Pachuta.
  */

@Service
class OrderService(orderDao: OrderDao) {

  def insert(order: Order): Unit = orderDao.insert(order)

  def getAll: Iterable[Order] = orderDao.getAll

}
