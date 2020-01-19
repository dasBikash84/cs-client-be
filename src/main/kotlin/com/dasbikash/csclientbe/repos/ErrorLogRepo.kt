package com.dasbikash.csclientbe.repos

import com.dasbikash.csclientbe.model.db.ErrorLog
import org.springframework.data.jpa.repository.JpaRepository

interface ErrorLogRepo:JpaRepository<ErrorLog,Long>