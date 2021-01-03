package pl.rgrodzicki.banking.transactions

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.rgrodzicki.banking.transactions.api.TransactionResponseDto
import pl.rgrodzicki.banking.transactions.api.WILDCARD

@RestController
class TransactionsController(
    private val transactionsService: TransactionsService,
) {

    @GetMapping
    fun find(
        @RequestParam(required = false) accountTypes: List<String>?,
        @RequestParam(required = false) customers: List<String>?
    ): List<TransactionResponseDto> =
        transactionsService.findTransactions(
            accountTypes.normalize(),
            customers.normalize()
        )

    private fun List<String>?.normalize(): TransactionsRequestParam {
        return if (this == null || this.isWildcard()) WildcardParam
        else IdsListRequestParam(this.map { it.toInt() })
    }

    private fun List<String>.isWildcard() = this.isEmpty() || this.first() == WILDCARD


}

sealed class TransactionsRequestParam

object WildcardParam : TransactionsRequestParam()

data class IdsListRequestParam(
    val ids: List<Int>
) : TransactionsRequestParam()
