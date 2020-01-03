package com.dasbikash.csclientbe.services


import com.dasbikash.csclientbe.exceptions.CsClientAuthenticationException
import com.dasbikash.csclientbe.repos.CustomerManagerRepository
import com.dasbikash.csclientbe.repos.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.security.auth.login.CredentialException

@Service("userDetailsService")
class CsClientUserDetailsService(
        private var userRepository: UserRepository?=null,
        private var customerManagerRepository: CustomerManagerRepository?=null
)
    : UserDetailsService {

    override fun loadUserByUsername(userName: String?): UserDetails? {
        println(userName)
        userName?.let {
            userRepository!!.findById(userName).apply {
                if (isPresent) {
                    println(get().getUserDetails())
                    return get().getUserDetails()
                }
            }
            customerManagerRepository!!.findById(userName).apply {
                if (isPresent) {
                    println(get().getUserDetails())
                    return get().getUserDetails()
                }
            }
        }
        return null
    }
}