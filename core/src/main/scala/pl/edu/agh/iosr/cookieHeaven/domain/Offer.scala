package pl.edu.agh.iosr.cookieHeaven.domain

import org.springframework.data.mongodb.core.mapping.Document

/**
  * @author Wojciech Pachuta.
  */
@Document
case class Offer(id: String, name: String, price: Double)
