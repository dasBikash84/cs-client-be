package com.dasbikash.csclientbe.utils

import org.apache.catalina.authenticator.BasicAuthenticator
import org.apache.tomcat.util.buf.ByteChunk
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.servlet.http.HttpServletRequest

object BasicAuthUtils {

    private const val BASIC_AUTH_HEADER_PREABLE = "Basic "

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

class AuthenticationRequest(val id: String, val password: String)

fun HttpServletRequest.getAuthorizationHeader():String?{
    return getHeader("Authorization")
}

fun UserDetails.isValid():Boolean{
    return isAccountNonExpired && isAccountNonLocked && isCredentialsNonExpired && isEnabled
}