package com.dasbikash.csclientbe.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.security.SecureRandom

@Configuration
class DefaultConfig {

    companion object{
        private const val SALT = "chatservererclientlabon"
    }

    @Bean
    fun  getPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder(12, SecureRandom(SALT.toByteArray()))
    }
}