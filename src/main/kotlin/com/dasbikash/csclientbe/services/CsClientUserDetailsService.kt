package com.dasbikash.csclientbe.services


import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service("userDetailsService")
class CsClientUserDetailsService(
)
    : UserDetailsService {

    override fun loadUserByUsername(userName: String?): UserDetails {
        TODO()
    }
}