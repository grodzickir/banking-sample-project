package pl.rgrodzicki.banking.transactions

import org.springframework.stereotype.Component
import pl.rgrodzicki.banking.transactions.api.TransactionResponseDto
import pl.rgrodzicki.banking.transactions.model.Customer
import pl.rgrodzicki.banking.transactions.model.Transaction

@Component
class TransactionResponseMapper {

    fun fromEntity(transaction: Transaction, customer: Customer) =
            TransactionResponseDto(
                    transactionId = transaction.id,
                    amount = transaction.amount,
                    accountTypeName = transaction.accountType.name,
                    customerFirstName = customer.firstName,
                    customerLastName = customer.lastName,
                    transactionDate = transaction.date
            )

}
