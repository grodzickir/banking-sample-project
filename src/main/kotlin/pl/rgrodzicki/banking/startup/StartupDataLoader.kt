package pl.rgrodzicki.banking.startup

import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import pl.rgrodzicki.banking.transactions.repository.CustomerRepository
import pl.rgrodzicki.banking.transactions.repository.TransactionRepository

@Component
class StartupDataLoader(
        private val csvDataReader: CsvDataReader,
        private val transactionRepository: TransactionRepository,
        private val customerRepository: CustomerRepository,
) {

    @EventListener
    fun onApplicationStartup(event: ContextRefreshedEvent) {
        clearCollections()
        csvDataReader.readTransactions().also {
            transactionRepository.saveAll(it)
        }
        csvDataReader.readCustomers().also {
            customerRepository.saveAll(it)
        }
    }

    private fun clearCollections() {
        transactionRepository.deleteAll()
        customerRepository.deleteAll()
    }

}
