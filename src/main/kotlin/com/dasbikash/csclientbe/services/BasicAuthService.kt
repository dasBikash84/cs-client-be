package com.dasbikash.csclientbe.services

import com.dasbikash.csclientbe.exceptions.CsClientAuthenticationException
import org.apache.catalina.authenticator.BasicAuthenticator
import org.apache.tomcat.util.buf.ByteChunk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
open class BasicAuthService @Autowired constructor(
        private val authenticationManager: AuthenticationManager
) {

    private val BASIC_AUTH_HEADER_PREABLE = "Basic "

    fun authenticateUser(request: HttpServletRequest){
        try {
            val authRequest = getAuthRequest(request)!!
            println(authRequest)
            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(authRequest.id, authRequest.password))
        } catch (ex: Throwable) {
            throw CsClientAuthenticationException(ex)
        }
    }

    fun getAuthRequest(request: HttpServletRequest): AuthenticationRequest? {
        request.getAuthorizationHeader()?.let {
            return decodeAuthRequest(it)
        }
        return null
    }

    private fun decodeAuthRequest(authHeader: String): AuthenticationRequest {
        val byteChunk = ByteChunk()
        byteChunk.setBytes(authHeader.toByteArray(), 0, authHeader.length)
        val basicAuthenticator =
                BasicAuthenticator.BasicCredentials(byteChunk, Charsets.UTF_8, true)
        return AuthenticationRequest(basicAuthenticator.username, basicAuthenticator.password)
    }

    fun getBasicAuthHeader(authenticationRequest: AuthenticationRequest):String{
        val authString = "${authenticationRequest.id}:${authenticationRequest.password}"
        return BASIC_AUTH_HEADER_PREABLE + Base64.getEncoder().encodeToString(authString.toByteArray())
    }
}

data class AuthenticationRequest(val id: String, val password: String)

fun HttpServletRequest.getAuthorizationHeader():String?{
    return getHeader("Authorization")
}

fun UserDetails.isValid():Boolean{
    return isAccountNonExpired && isAccountNonLocked && isCredentialsNonExpired && isEnabled
}