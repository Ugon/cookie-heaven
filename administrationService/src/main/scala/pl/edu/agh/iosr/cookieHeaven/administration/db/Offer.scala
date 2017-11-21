package pl.edu.agh.iosr.cookieHeaven.administration.db

import org.springframework.data.mongodb.core.mapping.Document

/**
  * @author Wojciech Pachuta.
  */
@Document
case class Offer(id: String, name: String, price: Double)
