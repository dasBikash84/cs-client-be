package com.dasbikash.csclientbe.services


import com.dasbikash.csclientbe.repos.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service("userDetailsService")
open class CsClientUserDetailsService @Autowired constructor(
        private val userRepository: UserRepository
)
    : UserDetailsService {

    override fun loadUserByUsername(userName: String?): UserDetails? {
        println(userName)
        userName?.let {
            userRepository.findById(userName).apply {
                if (isPresent) {
                    println(get().getUserDetails())
                    return get().getUserDetails()
                }
            }
        }
        return null
    }
}