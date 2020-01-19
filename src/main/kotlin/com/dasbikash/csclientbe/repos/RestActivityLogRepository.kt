package com.dasbikash.csclientbe.repos

import com.dasbikash.csclientbe.model.db.RestActivityLog
import org.springframework.data.jpa.repository.JpaRepository

interface RestActivityLogRepository : JpaRepository<RestActivityLog, Int>