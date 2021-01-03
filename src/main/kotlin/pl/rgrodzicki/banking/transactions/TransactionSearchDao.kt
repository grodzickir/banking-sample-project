package pl.rgrodzicki.banking.transactions

import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import pl.rgrodzicki.banking.transactions.model.Transaction

@Repository
class TransactionSearchDao(
    private val mongoTemplate: MongoTemplate
) {

    fun search(
        accountTypes: TransactionsRequestParam,
        customers: TransactionsRequestParam,
    ): List<Transaction> {
        val query = Query().apply {
            constructCriteria(accountTypes, customers)?.also {
                addCriteria(it)
            }
            with(Sort.by(Sort.Direction.ASC, "amount"))
        }
        return mongoTemplate.find(query, Transaction::class.java)
    }

    private fun constructCriteria(
        accountTypes: TransactionsRequestParam,
        customers: TransactionsRequestParam,
    ): Criteria? =
        listOfNotNull(
            accountTypes.toCriteria("accountType.id"),
            customers.toCriteria("customerId"),
        ).takeIf { it.isNotEmpty() }?.let {
            Criteria().andOperator(*it.toTypedArray())
        }

    private fun TransactionsRequestParam.toCriteria(key: String): Criteria? =
        if (this is IdsListRequestParam) {
            Criteria.where(key).`in`(this.ids)
        } else null

}
