package pl.edu.agh.iosr.cookieHeaven.administration.web

import java.io.IOException
import java.util
import javax.servlet.{FilterChain, ServletException}
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.{AuthenticationManager, UsernamePasswordAuthenticationToken}
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import pl.edu.agh.iosr.cookieHeaven.administration.web.JWTAuthenticationFilter._

class JWTAuthorizationFilter(val authManager: AuthenticationManager) extends BasicAuthenticationFilter(authManager) {

  @throws[IOException]
  @throws[ServletException]
  override protected def doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain): Unit = {
    val header = req.getHeader(HEADER_STRING)
    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(req, res)
      return
    }
    val authentication = getAuthentication(req)
    SecurityContextHolder.getContext.setAuthentication(authentication)
    chain.doFilter(req, res)
  }

  private def getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken = {
    val token = request.getHeader(HEADER_STRING)
    if (token != null) { // parse the token.
      val user = Jwts.parser.setSigningKey(Secret.getBytes).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody.getSubject
      if (user != null) return new UsernamePasswordAuthenticationToken(user, null, new util.ArrayList[GrantedAuthority])
      return null
    }
    null
  }
}