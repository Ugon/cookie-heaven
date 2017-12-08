package pl.edu.agh.iosr.cookieHeaven.notification.service

import java.io.StringWriter

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.{MailException, SimpleMailMessage}
import org.springframework.stereotype.Service
import pl.edu.agh.iosr.cookieHeaven.notification.db.{ScalaObjectMapper, Subscription}


@Service
class ReporterService()(mailSender :JavaMailSender) {

  val mapper = new ScalaObjectMapper

  def sendReport(subscription: Subscription): Unit = {
    val msg = new SimpleMailMessage()
    msg.setTo(subscription.email)
    msg.setSubject(s"cookie report for subscription ${subscription.id}")
    msg.setText(fetchReport(subscription.offerId))  //todo send json file as attachment
    try
      this.mailSender.send(msg)
    catch {
      case ex: MailException =>
        // simply log it and go on...
        System.err.println(ex.getMessage)
    }
  }

  def fetchReport(id: String): String = {
    val out = new StringWriter
    val client = new DefaultHttpClient()
    val response = client.execute(new HttpGet(s"http://localhost:8001/offers/$id/orders"))
    val json = mapper.readTree(response.getEntity.getContent)
    client.getConnectionManager.shutdown()
    mapper.writeValue(out, json)
    out.toString
  }
}