package pl.edu.agh.iosr.cookieHeaven.administration

import java.util

import com.fasterxml.jackson.databind.JsonNode
import org.junit.{Before, Test}
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pl.edu.agh.iosr.cookieHeaven.administration.db.{Offer, OfferRepository, ScalaObjectMapper}
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import pl.edu.agh.iosr.cookieHeaven.administration.service.OrderService
import org.mockito.Mockito.when

import scala.collection.JavaConverters._


@RunWith(classOf[SpringRunner])
@SpringBootTest(
  classes = Array(classOf[MainConfig]),
  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdministrationServiceIntegrationTest {

  @Autowired
  private val restTemplate = new TestRestTemplate

  @Autowired
  private val offerRepository: OfferRepository = null

  @MockBean
  private val orderService: OrderService = null

  val mapper = new ScalaObjectMapper

  @Before
  def setUp(): Unit = {
    offerRepository.deleteAll()
  }

  @Test
  def getShouldReturnEmptyList(): Unit = {
    val URL = "http://localhost:8001/offers"
    val offers = restTemplate.getForObject(URL, classOf[util.List[Offer]])
    assert(offers.isEmpty)
  }

  @Test
  def getShouldReturnOffer(): Unit = {
    val URL = "http://localhost:8001/offers/1"
    val offer = Offer("1", "Cookie", 2.5)
    offerRepository.insert(offer)
    val offer2 = restTemplate.getForObject(URL, classOf[Offer])
    assert(offer2.equals(offer))
  }

  @Test
  def getShouldReturnNotFoundError(): Unit = {
    val URL = "http://localhost:8001/offers/2"
    val offer = restTemplate.getForEntity(URL, classOf[Offer])
    assert(offer.getStatusCode.value() == 404)
  }

  @Test
  def postShouldInsertOffer(): Unit = {
    val URL = "http://localhost:8001/offers"
    val offer = Offer("1", "Cookie", 2.5)
    restTemplate.postForObject(URL, offer, classOf[Offer])
    assert(offerRepository.findById("1").equals(offer))
  }

  @Test
  def deleteShouldRemoveOffer(): Unit = {
    val URL = "http://localhost:8001/offers/1"
    when(orderService.listOrdersForOffer("1")).thenReturn(mapper.readTree("[]").asScala)
    val offer = Offer("1", "Cookie", 2.5)
    offerRepository.insert(offer)
    restTemplate.delete(URL)
    assert(offerRepository.findById("1") == null)
  }

  @Test
  def deleteShouldNotRemoveOffer(): Unit = {
    val URL = "http://localhost:8001/offers/1"
    when(orderService.listOrdersForOffer("1")).thenReturn(mapper.readTree("[" +
      "{\"id\": \"1\", \"firstname\": \"Ala\", \"lastname\": \"Makota\", \"offerId\": \"1\", \"quantity\": 5}," +
      "{\"id\": \"2\", \"firstname\": \"Jan\", \"lastname\": \"Kowalski\", \"offerId\": \"1\", \"quantity\": 7}" +
      "]"
    ).asScala)
    val offer = Offer("1", "Cookie", 2.5)
    offerRepository.insert(offer)
    restTemplate.delete(URL)
    assert(offerRepository.findById("1").equals(offer))
  }

  @Test
  def getShouldReturnOrdersForOffer(): Unit = {
    val URL = "http://localhost:8001/offers/1/orders"
    when(orderService.listOrdersForOffer("1")).thenReturn(mapper.readTree("[" +
      "{\"id\": \"1\", \"firstname\": \"Ala\", \"lastname\": \"Makota\", \"offerId\": \"1\", \"quantity\": 5}," +
      "{\"id\": \"2\", \"firstname\": \"Jan\", \"lastname\": \"Kowalski\", \"offerId\": \"1\", \"quantity\": 7}" +
    "]"
    ).asScala)
    val orders = restTemplate.getForObject(URL, classOf[Iterable[JsonNode]])
    assert(orders.size == 2)
  }
  
}
