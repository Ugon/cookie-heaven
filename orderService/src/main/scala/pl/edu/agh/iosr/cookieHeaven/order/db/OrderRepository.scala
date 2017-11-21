package pl.edu.agh.iosr.cookieHeaven.order.db

import java.util
import org.springframework.data.mongodb.repository.MongoRepository

trait OrderRepository extends MongoRepository[Order, String] {
  def findByOfferId(id: String): util.List[Order]
}
