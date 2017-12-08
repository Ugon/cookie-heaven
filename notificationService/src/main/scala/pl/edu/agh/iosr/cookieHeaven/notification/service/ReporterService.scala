package pl.edu.agh.iosr.cookieHeaven.notification.service

import java.io.{File, PrintWriter, StringWriter}
import java.util.Calendar

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.MailException
import org.springframework.mail.javamail.{JavaMailSender, MimeMessageHelper}
import org.springframework.stereotype.Service
import pl.edu.agh.iosr.cookieHeaven.notification.db.{ScalaObjectMapper, Subscription}


@Service
class ReporterService()(mailSender: JavaMailSender) {

  val mapper = new ScalaObjectMapper
  val tmpFile = "/tmp/tmp.json\""
  val reportFile = "report.json"
  val offerServiceUrl = "http://localhost:8001" //fixme hardcoded url

  def sendReport(subscription: Subscription): Unit = {
    val msg = mailSender.createMimeMessage()
    val helper = new MimeMessageHelper(msg, true)
    helper.setTo(subscription.email)
    helper.setSubject(s"cookie-heaven report for subscription ${subscription.id}")
    val report = fetchReport(subscription.offerId)

    helper.setText(s"Hello,\n\n" +
      s"attached you will find orders report for offer ${subscription.offerId} as from ${Calendar.getInstance.getTime}\n\n" +
      s"Regards\n" +
      s"cookie-heaven team")

    new PrintWriter(tmpFile) {
      write(report); close()
    }

    val file = new FileSystemResource(new File(tmpFile))
    helper.addAttachment(reportFile, file)
    try
      this.mailSender.send(msg)
    catch {
      case ex: MailException =>
        System.err.println(ex.getMessage)
    }
  }

  def fetchReport(id: String): String = {
    val out = new StringWriter
    val client = new DefaultHttpClient()
    val response = client.execute(new HttpGet(s"$offerServiceUrl/offers/$id/orders"))
    val json = mapper.readTree(response.getEntity.getContent)
    client.getConnectionManager.shutdown()
    mapper.writeValue(out, json)
    out.toString
  }
}