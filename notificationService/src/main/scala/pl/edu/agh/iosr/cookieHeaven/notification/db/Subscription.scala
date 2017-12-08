package pl.edu.agh.iosr.cookieHeaven.notification.db

import org.springframework.data.mongodb.core.mapping.Document

@Document
case class Subscription(id: String, offerId: String, email: String)
