package pl.rgrodzicki.banking.config.security

import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
data class UserCredentials(
    val username: String,
    val password: String,
)
