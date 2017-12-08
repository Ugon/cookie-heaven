package pl.edu.agh.iosr.cookieHeaven.notification.service

import java.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.agh.iosr.cookieHeaven.notification.db.{Subscription, SubscriptionRepository}

@Service
class NotificationService @Autowired() (subscriptionRepository: SubscriptionRepository){

  def list(): util.List[Subscription] = subscriptionRepository.findAll()

  def get(id: String): Subscription = subscriptionRepository.findById(id)

  def add(subscription: Subscription): Subscription = subscriptionRepository.insert(subscription)

  def remove(id: String): Unit = subscriptionRepository.delete(id)

}
