package pl.edu.agh.iosr.cookieHeaven.order.web

import java.io.IOException
import java.util
import java.util.Date
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import javax.servlet.{FilterChain, ServletException}

import io.jsonwebtoken.{Jwts, SignatureAlgorithm}
import org.springframework.security.authentication.{AuthenticationManager, UsernamePasswordAuthenticationToken}
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.{Authentication, AuthenticationException, GrantedAuthority}
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import pl.edu.agh.iosr.cookieHeaven.order.db.ServiceUser
import pl.edu.agh.iosr.cookieHeaven.order.web.JWTAuthenticationFilter._

class JWTAuthenticationFilter(var authenticationManager: AuthenticationManager) extends UsernamePasswordAuthenticationFilter {

  @throws[AuthenticationException]
  override def attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication = try {
    val creds = ServiceUser(req.getParameter("login"), req.getParameter("pass"))
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.login, creds.password, new util.ArrayList[GrantedAuthority]))
  } catch {
    case e: IOException =>
      throw new RuntimeException(e)
  }

  @throws[IOException]
  @throws[ServletException]
  override protected def successfulAuthentication(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain, auth: Authentication): Unit = {
    val token = Jwts.builder.setSubject(auth.getPrincipal.asInstanceOf[User].getUsername)
      .setExpiration(new Date(System.currentTimeMillis + ExpirationMillis))
      .signWith(SignatureAlgorithm.HS512, Secret.getBytes)
      .compact
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
  }
}

object JWTAuthenticationFilter {
  val ExpirationMillis: Long = 2 * 60 * 60 * 1000
  val Secret = "SecretKeyToGenJWTs"
  val TOKEN_PREFIX = "Bearer "
  val HEADER_STRING = "Authorization"
}