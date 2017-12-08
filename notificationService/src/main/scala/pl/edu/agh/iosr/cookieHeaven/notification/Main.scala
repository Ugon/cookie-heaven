package pl.edu.agh.iosr.cookieHeaven.notification


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Bean, Primary}
import org.springframework.scheduling.annotation.EnableScheduling
import pl.edu.agh.iosr.cookieHeaven.notification.db.ScalaObjectMapper

@SpringBootApplication
@EnableScheduling
class MainConfig {
  @Bean
  @Primary
  def scalaObjectMapper() = new ScalaObjectMapper
}

object Main extends App {

  SpringApplication.run(classOf[MainConfig])

}
