package pl.edu.agh.iosr.cookieHeaven.order.web

import java.util.Collections.emptyList

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.{User, UserDetails, UserDetailsService, UsernameNotFoundException}
import org.springframework.stereotype.Service
import pl.edu.agh.iosr.cookieHeaven.order.service.UserService


@Service class UserDetailsServiceImpl(var userService: UserService) extends UserDetailsService {
  @throws[UsernameNotFoundException]
  override def loadUserByUsername(username: String): UserDetails = {
    val applicationUser = userService.getByLogin(username)
    if (applicationUser == null) throw new UsernameNotFoundException(username)
    new User(applicationUser.login, applicationUser.password, emptyList.asInstanceOf[java.util.Collection[GrantedAuthority]])
  }
}