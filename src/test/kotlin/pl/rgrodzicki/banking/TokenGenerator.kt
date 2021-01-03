package pl.rgrodzicki.banking

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.StringSpec
import java.util.*

@Ignored
class TokenGenerator : StringSpec() {

    init {
        "generate" {
            println(generateToken("test", "test"))
        }
    }

    private fun generateToken(username: String, password: String) =
        String(Base64.getEncoder().encode("$username:$password".toByteArray()))

}
