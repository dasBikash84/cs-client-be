package com.dasbikash.csclientbe.services

import com.dasbikash.csclientbe.exceptions.CsClientAuthenticationException
import org.apache.catalina.authenticator.BasicAuthenticator
import org.apache.tomcat.util.buf.ByteChunk
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
open class BasicAuthService() {

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

    companion object{
        class AuthenticationRequest(val id: String, val password: String)
    }
}

fun HttpServletRequest.getAuthorizationHeader():String?{
    return getHeader("Authorization")
}

fun UserDetails.isValid():Boolean{
    return isAccountNonExpired && isAccountNonLocked && isCredentialsNonExpired && isEnabled
}