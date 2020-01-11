package com.dasbikash.csclientbe.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.client.RestTemplate
import java.security.SecureRandom

@Configuration
open class DefaultConfig {

    companion object{
        private const val SALT = "chatservererclientlabon"
    }

    @Bean
    open fun  getPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder(12, SecureRandom(SALT.toByteArray()))
    }
}