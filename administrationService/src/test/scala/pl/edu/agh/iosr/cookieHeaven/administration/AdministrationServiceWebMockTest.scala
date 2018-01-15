package pl.edu.agh.iosr.cookieHeaven.administration
import java.util

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.when
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import pl.edu.agh.iosr.cookieHeaven.administration.db.{Offer, ScalaObjectMapper}
import pl.edu.agh.iosr.cookieHeaven.administration.service.{OfferService, OrderService}
import pl.edu.agh.iosr.cookieHeaven.administration.web.OfferController
import org.mockito.Mockito.when
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(classOf[SpringRunner])
@WebMvcTest(Array(classOf[OfferController]))
class AdministrationServiceWebMockTest {

    val testOffer1 = Offer("1", "testcookie1", 1.23)
    val testOffer2 = Offer("2", "testcookie2", 9.71)
    val testOffer3 = Offer("3", "testcookie3", 123.123)
    val testOffer4 = Offer("4", "testcookie4", 4235)
    val offers: util.List[Offer] = util.Arrays.asList(testOffer1, testOffer2, testOffer3, testOffer4)


    @Autowired
    var mockMvc: MockMvc = _

    val mapper = new ScalaObjectMapper()

    @MockBean
    var offerService: OfferService = _
    @MockBean
    var orderService: OrderService = _

    @Test
    def getWithOfferIdShouldReturnOffer(): Unit = {
        when(offerService.get("1")).thenReturn(testOffer1)
        when(offerService.get("2")).thenReturn(testOffer2)

        mockMvc.perform(get("/offers/1"))
          .andExpect(status().isOk)
          .andExpect(content().json("{'id': '1', 'name':'testcookie1', 'price': 1.23}"))

        mockMvc.perform(get("/offers/2"))
          .andExpect(status().isOk)
          .andExpect(content().json("{'id': '2', 'name':'testcookie2', 'price': 9.71}"))
    }

    @Test
    def getShouldReturnNotFoundError(): Unit =
        mockMvc.perform(get("/offers/3")).andExpect(status().isNotFound)

    @Test
    def getShouldReturnListOfOffers(): Unit = {
        when(offerService.list()).thenReturn(offers)
        mockMvc.perform(get("/offers"))
            .andExpect(status().isOk)
            .andExpect(content().json(mapper.writeValueAsString(offers)))
    }
}