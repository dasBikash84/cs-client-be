package com.dasbikash.csclientbe.repos

import com.dasbikash.csclientbe.model.db.CustomerManager
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerManagerRepository:JpaRepository<CustomerManager,String> {
}