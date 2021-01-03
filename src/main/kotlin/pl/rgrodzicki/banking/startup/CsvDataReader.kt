package pl.rgrodzicki.banking.startup

import com.opencsv.CSVParser
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import pl.rgrodzicki.banking.transactions.model.AccountType
import pl.rgrodzicki.banking.transactions.model.Customer
import pl.rgrodzicki.banking.transactions.model.Transaction
import java.io.File
import java.io.FileReader

@Component
class CsvDataReader {

    private val customersFile = ResourceUtils.getFile("classpath:customers.csv")
    private val transactionsFile = ResourceUtils.getFile("classpath:transactions.csv")
    private val accountTypesFile = ResourceUtils.getFile("classpath:accountypes.csv")

    fun readCustomers(): List<Customer> = constructReader(customersFile)
            .filterNot(::emptyLine)
            .map(::stringsToCustomer)


    fun readTransactions(): List<Transaction> {
        val accountTypes = readAccountTypes()
        return constructReader(transactionsFile)
                .filterNot(::emptyLine)
                .map(::stringsToTransaction)
                .map { it.toTransaction(accountTypes) }
    }

    private fun readAccountTypes(): List<AccountType> = constructReader(accountTypesFile)
            .filterNot(::emptyLine)
            .map(::stringsToAccountType)

    private fun constructReader(file: File): CSVReader {
        return CSVReaderBuilder(FileReader(file))
                .withSkipLines(1)
                .withCSVParser(CSVParser())
                .build()
    }

    private fun emptyLine(line: Array<String>) = line.all { it.isEmpty() }

}
