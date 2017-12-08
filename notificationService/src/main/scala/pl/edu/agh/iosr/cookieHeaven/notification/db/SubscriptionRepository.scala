package pl.edu.agh.iosr.cookieHeaven.notification.db

import org.springframework.data.mongodb.repository.MongoRepository

trait SubscriptionRepository extends MongoRepository[Subscription, String]{
  def findById(id: String): Subscription
}
