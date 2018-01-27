package pl.edu.agh.iosr.cookieHeaven.administration

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Bean, Primary}
import org.springframework.web.client.RestTemplate
import pl.edu.agh.iosr.cookieHeaven.administration.db.ScalaObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
@EnableWebSecurity
class MainConfig {
  @Bean
  @Primary
  def scalaObjectMapper() = new ScalaObjectMapper

  @Bean def restTemplate = new RestTemplate()

  @Bean def bCryptPasswordEncoder = new BCryptPasswordEncoder()
}

object MainAdmin extends App {
  SpringApplication.run(classOf[MainConfig])
}