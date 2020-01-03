package com.dasbikash.csclientbe.filters

import com.dasbikash.csclientbe.utils.BasicAuthUtils
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class BasicAuthenticationFilter(open var userDetailsService: UserDetailsService)
    : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        BasicAuthUtils.getAuthRequest(request)?.let {
            userDetailsService.loadUserByUsername(it.id)?.apply {
                val usernamePasswordAuthenticationToken =
                        UsernamePasswordAuthenticationToken(this, null, this.getAuthorities())
                usernamePasswordAuthenticationToken
                        .setDetails(WebAuthenticationDetailsSource().buildDetails(request))
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken)
            }
        }
        chain.doFilter(request, response)
    }

}