package pl.edu.agh.iosr.cookieHeaven.domain

import org.bson.types.ObjectId

import scala.beans.BeanProperty

/**
  * @author Wojciech Pachuta.
  */
case class Order(@BeanProperty id: ObjectId,
                 @BeanProperty name: String)
