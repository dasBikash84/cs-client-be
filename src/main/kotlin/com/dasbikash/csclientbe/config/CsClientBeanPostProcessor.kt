package com.dasbikash.csclientbe.config

import com.dasbikash.csclientbe.utils.consoleLog
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component


@Component
open class CsClientBeanPostProcessor: BeanPostProcessor {
    @Throws(BeansException::class)
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        return bean
    }

    @Throws(BeansException::class)
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        consoleLog("created bean: $beanName of class: ${bean.javaClass.canonicalName}")
        return bean
    }
}