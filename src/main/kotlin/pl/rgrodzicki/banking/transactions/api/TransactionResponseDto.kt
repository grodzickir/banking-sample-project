package pl.rgrodzicki.banking.transactions.api

import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionResponseDto(
    val transactionId: Int,
    val amount: BigDecimal,
    val accountTypeName: String,
    val customerFirstName: String,
    val customerLastName: String,
    val transactionDate: LocalDateTime,
)

val WILDCARD = "ALL"
