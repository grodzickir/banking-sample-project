package pl.rgrodzicki.banking.transactions.repository

import org.springframework.data.mongodb.repository.MongoRepository
import pl.rgrodzicki.banking.transactions.model.Transaction

interface TransactionRepository : MongoRepository<Transaction, Int> {
}
