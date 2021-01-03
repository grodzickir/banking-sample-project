package pl.rgrodzicki.banking.startup

import pl.rgrodzicki.banking.transactions.model.AccountType
import pl.rgrodzicki.banking.transactions.model.Customer
import pl.rgrodzicki.banking.transactions.model.Transaction
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun stringsToCustomer(input: Array<String>) =
    Customer(
        id = input[0].toInt(),
        firstName = input[1],
        lastName = input[2],
        lastLoginBalance = input[3].toBigDecimalPolishNotation()
    )

fun stringsToTransaction(input: Array<String>) =
        TransactionReadDto(
                id = input[0].toInt(),
                amount = input[1].toBigDecimalPolishNotation(),
                accountTypeId = input[2].toInt(),
                customerId = input[3].toInt(),
                date = LocalDateTime.parse(input[4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )

fun stringsToAccountType(input: Array<String>) =
        AccountType(
                input[0].toInt(),
                input[1]
        )

data class TransactionReadDto(
        val id: Int,
        val amount: BigDecimal,
        val accountTypeId: Int,
        val customerId: Int,
        val date: LocalDateTime,
) {
    fun toTransaction(accountTypes: List<AccountType>) =
            Transaction(
                    id = this.id,
                    amount = this.amount,
                    accountType = accountTypes.single { it.id == this.accountTypeId },
                    customerId = this.customerId,
                    date = this.date
            )
}

private fun String.toBigDecimalPolishNotation() = this.replace(',', '.').toBigDecimal()
