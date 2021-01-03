package pl.rgrodzicki.banking.transactions.model

import org.springframework.data.annotation.Id

data class AccountType(
    @Id
    val id: Int,
    val name: String,
)
