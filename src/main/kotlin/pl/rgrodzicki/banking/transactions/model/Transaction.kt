package pl.rgrodzicki.banking.transactions.model

import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
        @Id
        val id: Int,
        val amount: BigDecimal,
        val accountType: AccountType,
        val customerId: Int,
        val date: LocalDateTime,
)
