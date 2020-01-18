package com.dasbikash.csclientbe.model.db

import com.dasbikash.csclientbe.config.UserAuthorities
import com.dasbikash.csclientbe.model.request.CsUserRegisterRequest
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Transient

@Entity
data class User(
        @Id
        var userId:String?=null,
        var password:String?=null,
        var firstName:String?=null,
        var lastName:String?=null,
        var isCustomerManager: Boolean=false,
        var isEndUser: Boolean=true,
        @JsonIgnore
        var registeredToCs: Boolean = false,
        @JsonIgnore
        var enabled: Boolean=true,
        @JsonIgnore
        var expired: Boolean=false,
        @JsonIgnore
        var locked: Boolean=false,
        @JsonIgnore
        var credentialsExpired: Boolean=false,
        @JsonIgnore
        @Column(nullable = false, updatable=false,insertable = false)
        var modified: Date?=null,
        @JsonIgnore
        @Column(nullable = false, updatable=false,insertable = false)
        var created: Date?=null
) {
    @Transient
    @JsonIgnore
    fun getUserDetails(): UserDetails {
        return object : UserDetails {
            override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
                return mutableListOf(object : GrantedAuthority {
                    override fun getAuthority():String{
                        if (isCustomerManager){
                            return UserAuthorities.CM.name
                        }else {
                            return UserAuthorities.EndUSER.name
                        }
                    }
                })
            }

            override fun isEnabled() = enabled
            override fun getUsername() = userId
            override fun isCredentialsNonExpired() = true
            override fun getPassword(): String = this@User.password!!
            override fun isAccountNonExpired(): Boolean = true
            override fun isAccountNonLocked(): Boolean = true
        }
    }

    @Transient
    @JsonIgnore
    fun getUserRegisterRequest(): CsUserRegisterRequest {
        return CsUserRegisterRequest(userId!!,"Full name: $firstName $lastName")
    }
}