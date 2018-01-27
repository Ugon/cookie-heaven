package pl.edu.agh.iosr.cookieHeaven.administration.db

import org.springframework.data.mongodb.repository.MongoRepository


trait UserRepository extends MongoRepository[ServiceUser, String] {
  def findByLogin(login: String): ServiceUser
}
