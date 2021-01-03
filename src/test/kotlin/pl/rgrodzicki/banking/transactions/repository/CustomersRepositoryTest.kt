package pl.rgrodzicki.banking.transactions.repository

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.spring.SpringListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CustomersRepositoryTest : StringSpec() {

    override fun listeners() = listOf(SpringListener)

    @Autowired
    private lateinit var subject: CustomerRepository

    init {
        "get customers" {
            val customers = subject.findAll()
            customers.count() shouldBe 5
            val kowalski = customers.find { it.lastName == "Kowalski" }
            kowalski.shouldNotBeNull().apply {
                lastLoginBalance shouldBe 533.99.toBigDecimal()
            }
        }
    }

}
