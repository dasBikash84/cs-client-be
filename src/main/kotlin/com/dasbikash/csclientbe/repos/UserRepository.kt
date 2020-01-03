package com.dasbikash.csclientbe.repos

import com.dasbikash.csclientbe.model.db.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository:JpaRepository<User,String> {
    fun getAllByRegisteredToCs(registered:Boolean=false):List<User>
}