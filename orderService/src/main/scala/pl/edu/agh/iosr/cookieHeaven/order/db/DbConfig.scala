package pl.edu.agh.iosr.cookieHeaven.order.db

import com.mongodb.{MongoClient, ServerAddress}
import com.mongodb.client.MongoDatabase
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * @author Wojciech Pachuta.
  */

@Configuration
class DbConfig {

  val MongoIp = "localhost"
  val MongoPort = 30000

  @Bean def mongoClient: MongoClient = new MongoClient(new ServerAddress(MongoIp, MongoPort))

  @Bean def mongoDatabase: MongoDatabase = mongoClient.getDatabase("Order")

  @Bean def orderDao: OrderDao = OrderDao.create(mongoDatabase)

}
