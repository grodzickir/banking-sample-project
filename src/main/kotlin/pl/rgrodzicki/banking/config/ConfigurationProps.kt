package pl.rgrodzicki.banking.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import pl.rgrodzicki.banking.config.security.UserCredentials


@Configuration
@ConfigurationProperties(prefix = "application")
class ConfigurationProps {

    var users: List<UserCredentials> = emptyList()

}
