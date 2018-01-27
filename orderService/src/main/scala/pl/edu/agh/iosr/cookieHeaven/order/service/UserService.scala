package pl.edu.agh.iosr.cookieHeaven.order.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.agh.iosr.cookieHeaven.order.db.{ServiceUser, UserRepository}

@Service
class UserService @Autowired()(userRepository: UserRepository) {

  def getByLogin(login: String): ServiceUser = userRepository.findByLogin(login)

  def add(user: ServiceUser): ServiceUser = userRepository.insert[ServiceUser](user)

}
