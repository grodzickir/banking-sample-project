package pl.rgrodzicki.banking.transactions.model

import org.springframework.data.annotation.Id
import java.math.BigDecimal

class Customer(
    @Id
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val lastLoginBalance: BigDecimal,
)
