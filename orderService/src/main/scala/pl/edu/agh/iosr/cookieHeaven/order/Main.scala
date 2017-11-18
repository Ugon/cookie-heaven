package pl.edu.agh.iosr.cookieHeaven.order

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{ComponentScan, Configuration}


@Configuration
@EnableAutoConfiguration
@ComponentScan
class MainConfig

object Main {

  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[MainConfig])
  }

}