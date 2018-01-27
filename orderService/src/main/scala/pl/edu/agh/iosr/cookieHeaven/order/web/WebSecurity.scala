package pl.edu.agh.iosr.cookieHeaven.order.web

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.{CorsConfiguration, CorsConfigurationSource, UrlBasedCorsConfigurationSource}


@EnableWebSecurity class WebSecurity(val uds: UserDetailsService, var bCryptPasswordEncoder: BCryptPasswordEncoder) extends WebSecurityConfigurerAdapter {

  override val userDetailsService: UserDetailsService = uds

  @throws[Exception]
  override protected def configure(http: HttpSecurity): Unit = {
    http.cors.and.csrf.disable.authorizeRequests
      .antMatchers(HttpMethod.POST, "/orders/users").permitAll
      .anyRequest
      .authenticated
      .and
      .addFilter(new JWTAuthenticationFilter(authenticationManager))
      .addFilter(new JWTAuthorizationFilter(authenticationManager))
      .sessionManagement // this disables session creation on Spring Security
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  }

  @throws[Exception]
  override def configure(auth: AuthenticationManagerBuilder): Unit = {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder)
  }

  @Bean def corsConfigurationSource: CorsConfigurationSource = {
    val source = new UrlBasedCorsConfigurationSource
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues)
    source
  }
}