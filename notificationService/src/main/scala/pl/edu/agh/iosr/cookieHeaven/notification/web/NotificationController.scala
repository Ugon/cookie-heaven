package pl.edu.agh.iosr.cookieHeaven.notification.web

import java.util

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation._
import pl.edu.agh.iosr.cookieHeaven.notification.db.{ScalaObjectMapper, Subscription}
import pl.edu.agh.iosr.cookieHeaven.notification.service.{NotificationService, ReporterService}

import scala.collection.JavaConverters._

@RestController
@RequestMapping(Array("/subscriptions"))
class NotificationController @Autowired()(notificationService: NotificationService,
                                          reporterService: ReporterService) {

  @Value("${misc.message}")
  var message: String = _

  val logger: Logger = LoggerFactory.getLogger(this.getClass)
  val mapper = new ScalaObjectMapper

  @GetMapping(Array("message"))
  def msg: String = message

  @GetMapping
  def list: util.List[Subscription] = notificationService.list()

  @PostMapping
  def add(@RequestBody subscription: Subscription): Subscription =
    notificationService.add(subscription)

  @GetMapping(Array("{id}"))
  def get(@PathVariable id: String): Subscription = notificationService.get(id)

  @DeleteMapping(Array("{id}"))
  def delete(@PathVariable id: String): Unit = notificationService.remove(id)


  @Scheduled(fixedRate = 5000)
  def generateReport(): Unit = {

    val subscriptions = notificationService.list().asScala
    subscriptions.foreach {
      reporterService.sendReport
    }
  }

}