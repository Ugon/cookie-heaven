package pl.edu.agh.iosr.cookieHeaven.administration.db

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * @author Wojciech Pachuta.
  */

@Configuration
class DbConfig {

  val MongoIp = "localhost"
  val MongoPort = 30001

  @Bean def mongoClient: MongoClient = new MongoClient(s"mongodb://$MongoIp:$MongoPort")

  @Bean def mongoDatabase: MongoDatabase = mongoClient.getDatabase("Order")

  @Bean def dao: OfferDao = OfferDao.create(mongoDatabase)

}
