package pl.rgrodzicki.banking.startup

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeEmpty

class CsvDataReaderTest : StringSpec() {

    private val subject = CsvDataReader()

    init {

        "read customers" {
            val customers = subject.readCustomers()
            customers.count() shouldBe 5
            customers.forEach {
                it.firstName.shouldNotBeEmpty()
                it.lastName.shouldNotBeEmpty()
            }
        }

        "read account types" {
            val transactions = subject.readTransactions()
            transactions.count() shouldBe 4
        }

    }

}
