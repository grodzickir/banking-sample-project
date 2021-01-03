package pl.rgrodzicki.banking.transactions.repository

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.spring.SpringListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TransactionRepositoryTest : StringSpec() {

    override fun listeners() = listOf(SpringListener)

    @Autowired
    private lateinit var subject: TransactionRepository

    init {

        "get account types" {
            val transactions = subject.findAll()
            transactions.count() shouldBe 23
        }

    }

}
