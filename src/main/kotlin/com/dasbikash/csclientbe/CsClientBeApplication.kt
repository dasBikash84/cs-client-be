package com.dasbikash.csclientbe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class CsClientBeApplication: SpringBootServletInitializer() {

	override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder? {
		return application.sources(CsClientBeApplication::class.java)
	}
}

fun main(args: Array<String>) {
	runApplication<CsClientBeApplication>(*args)
}
