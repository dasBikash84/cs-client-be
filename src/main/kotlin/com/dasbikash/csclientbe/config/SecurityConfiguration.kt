package com.dasbikash.csclientbe.config


import com.dasbikash.csclientbe.filters.BasicAuthenticationFilter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class SecurityConfiguration(
        @Qualifier("userDetailsService")
        open var userDetailsService: UserDetailsService,
        open var basicAuthenticationFilter: BasicAuthenticationFilter
): WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
                .csrf().disable()
                .formLogin().disable()
                .authorizeRequests()
                .antMatchers(OPEN_API_PATH).permitAll()
                .antMatchers(HttpMethod.GET,SWAGGER_UI_PATH,BASE_HTML_PATH).permitAll()
                .antMatchers("/${ContextPathUtils.AUTH_CONTROLLER_BASE_PATH}/**").permitAll()
                .antMatchers("/${ContextPathUtils.CM_CONTROLLER_BASE_PATH}/**").hasAuthority(UserAuthorities.CM.name)
                .antMatchers("/${ContextPathUtils.USER_CONTROLLER_BASE_PATH}/**").hasAuthority(UserAuthorities.EndUSER.name)
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        httpSecurity.addFilterBefore(basicAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
    companion object{
        private const val OPEN_API_PATH = "/v3/api-docs/**"
        private const val BASE_HTML_PATH = "/swagger-ui.html"
        private const val SWAGGER_UI_PATH = "/swagger-ui/**"
    }
}