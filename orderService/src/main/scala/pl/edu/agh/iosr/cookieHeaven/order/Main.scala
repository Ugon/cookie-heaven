package pl.edu.agh.iosr.cookieHeaven.order

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Bean, Primary}
import pl.edu.agh.iosr.cookieHeaven.order.db.ScalaObjectMapper


@SpringBootApplication
class MainConfig {
  @Bean
  @Primary
  def scalaObjectMapper() = new ScalaObjectMapper
}

object Main extends App {
  SpringApplication.run(classOf[MainConfig])
}