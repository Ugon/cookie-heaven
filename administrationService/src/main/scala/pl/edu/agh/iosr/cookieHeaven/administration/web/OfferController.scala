package pl.edu.agh.iosr.cookieHeaven.administration.web

import java.util

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.cloud.sleuth.SpanAccessor
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation._
import pl.edu.agh.iosr.cookieHeaven.administration.db.{Offer, ServiceUser}
import pl.edu.agh.iosr.cookieHeaven.administration.service.{OfferService, OrderService, UserService}


/**
  * @author Wojciech Pachuta
  * @author Jakub Kudzia
  */

@RestController
@RequestMapping(Array("/offers"))
class OfferController @Autowired()(offerService: OfferService, orderService: OrderService, userService: UserService) {

  @Autowired var spanAccessor: SpanAccessor = _

  private val LOGGER = LoggerFactory.getLogger(classOf[OfferController])
  @Value("${misc.message}")
  var message: String = _

  @GetMapping
  def listOffers: util.List[Offer] = {
    LOGGER.info("listOffers")
    offerService.list()
  }

  @PostMapping
  def add(@RequestBody offer: Offer): Unit = offerService.add(offer)

  @GetMapping(Array("message"))
  def msg: String = {
    LOGGER.info("message")
    message
  }

  @GetMapping(Array("{id}"))
  def get(@PathVariable id: String): Offer = {
    val offer = offerService.get(id)
    if (offer == null)
      throw new OfferNotFoundException(s"Not found offer with id: $id")
    else
      offer
  }

  @DeleteMapping(Array("{id}"))
  def delete(@PathVariable id: String): Unit = {
    val orders = listOrdersForOffer(id)
    if (orders.isEmpty)
      offerService.remove(id)
    else
      throw new ExistingOrdersException(s"Orders associated with offer $id exist")
  }

  @GetMapping(Array("{id}/orders"))
  def listOrdersForOffer(@PathVariable id: String): String =
    orderService.listOrdersForOffer(id)

  @PostMapping(Array("users"))
  def registerUser(@RequestParam login: String, @RequestParam pass: String): Unit = {
    userService.add(ServiceUser(login, new BCryptPasswordEncoder().encode(pass)))
  }


  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  def handleException(offerNotFoundException: OfferNotFoundException): String = {
    offerNotFoundException.getMessage
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  def handleException(existingOrdersException: ExistingOrdersException): String = {
    existingOrdersException.getMessage
  }

}

class OfferNotFoundException(message: String) extends RuntimeException

class ExistingOrdersException(message: String) extends RuntimeException

