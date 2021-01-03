package pl.rgrodzicki.banking.transactions

import org.springframework.stereotype.Service
import pl.rgrodzicki.banking.transactions.api.TransactionResponseDto
import pl.rgrodzicki.banking.transactions.repository.CustomerRepository

@Service
class TransactionsService(
    private val customerRepository: CustomerRepository,
    private val transactionResponseMapper: TransactionResponseMapper,
    private val transactionSearchDao: TransactionSearchDao,
) {
    fun findTransactions(
        accountTypes: TransactionsRequestParam,
        customers: TransactionsRequestParam,
    ): List<TransactionResponseDto> =
        transactionSearchDao.search(accountTypes, customers)
            .map {
                val customer = customerRepository.findById(it.customerId)
                    .orElseThrow { IllegalStateException("No customer with id: ${it.customerId}") }
                transactionResponseMapper.fromEntity(it, customer)
            }

}
