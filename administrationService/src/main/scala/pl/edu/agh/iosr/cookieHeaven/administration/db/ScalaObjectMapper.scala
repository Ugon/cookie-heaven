package pl.edu.agh.iosr.cookieHeaven.administration.db

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

class ScalaObjectMapper extends ObjectMapper {

  registerModule(DefaultScalaModule)
}