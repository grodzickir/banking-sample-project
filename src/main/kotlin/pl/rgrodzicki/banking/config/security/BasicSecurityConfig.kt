package pl.rgrodzicki.banking.config.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import pl.rgrodzicki.banking.config.ConfigurationProps


@Configuration
class BasicSecurityConfig(
    private val basicAuthenticationProvider: BasicAuthenticationProvider,
) : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(basicAuthenticationProvider)
    }

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
    }
}

@Service
class BasicAuthenticationProvider(
    private val configurationProps: ConfigurationProps,
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials.toString()

        return configurationProps.users.find {
            it.username == username
        }?.let {
            if (password == it.password) {
                UsernamePasswordAuthenticationToken(
                    it.username,
                    it.password,
                    listOf(SimpleGrantedAuthority("USER"))
                )
            } else throw BadCredentialsException("Invalid password")
        } ?: throw UsernameNotFoundException(username)
    }

    override fun supports(authentication: Class<*>?): Boolean =
        authentication == UsernamePasswordAuthenticationToken::class.java
}
