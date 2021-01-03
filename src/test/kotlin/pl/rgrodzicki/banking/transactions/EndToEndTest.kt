package pl.rgrodzicki.banking.transactions

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.spring.SpringListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pl.rgrodzicki.banking.transactions.api.TransactionResponseDto


@SpringBootTest
@AutoConfigureMockMvc
class EndToEndTest : StringSpec() {

    override fun listeners() = listOf(SpringListener)

    @Autowired
    private lateinit var mockMvc: MockMvc

    init {
        "query by account type and customer id" {
            val transactions = mockMvc.perform(
                get("http://localhost:8080")
                    .param("accountTypes", "1")
                    .param("customers", "1")
                    .header("Authorization", "Basic dGVzdDp0ZXN0")
            ).andExpect(status().isOk)
                .andReturn().response.contentAsString.toTransactionList()
            transactions.count() shouldBe 4
            transactions.forEach {
                it.accountTypeName shouldBe "checking account"
                it.customerFirstName shouldBe "Andrzej"
            }
        }
        "query by customer id only" {
            val transactions = mockMvc.perform(
                get("http://localhost:8080")
                    .param("customers", "3")
                    .header("Authorization", "Basic dGVzdDp0ZXN0")
            ).andExpect(status().isOk)
                .andReturn().response.contentAsString.toTransactionList()
            transactions.count() shouldBe 4
            transactions.forEach {
                it.customerFirstName shouldBe "Marek"
            }
        }
        "query for multiple customers" {
            val transactions = mockMvc.perform(
                get("http://localhost:8080")
                    .param("customers", "3", "4", "5")
                    .header("Authorization", "Basic dGVzdDp0ZXN0")
            ).andExpect(status().isOk)
                .andReturn().response.contentAsString.toTransactionList()
            transactions.count() shouldBe 11
            transactions.map { it.customerLastName }
                .distinct()
                .count() shouldBe 3
        }
        "query for all " {
            val transactions = mockMvc.perform(
                get("http://localhost:8080")
                    .header("Authorization", "Basic dGVzdDp0ZXN0")
            ).andExpect(status().isOk)
                .andReturn().response.contentAsString.toTransactionList()
            transactions.count() shouldBe 23
        }
        "query for all with ALL param wildcards" {
            val transactions = mockMvc.perform(
                get("http://localhost:8080")
                    .param("accountTypes", "ALL")
                    .param("customers", "ALL")
                    .header("Authorization", "Basic dGVzdDp0ZXN0")
            ).andExpect(status().isOk)
                .andReturn().response.contentAsString.toTransactionList()
            transactions.count() shouldBe 23
        }
    }

    private fun String.toTransactionList() =
        transactionsMapper.readValue<List<TransactionResponseDto>>(this)

    private val transactionsMapper = jacksonObjectMapper()
        .registerModule(JavaTimeModule())
        .readerForListOf(TransactionResponseDto::class.java)

}
