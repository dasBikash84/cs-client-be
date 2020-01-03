package com.dasbikash.csclientbe.services

import com.dasbikash.csclientbe.model.request.CsUserRegisterRequest
import com.dasbikash.csclientbe.repos.UserRepository
import com.dasbikash.csclientbe.utils.BgTaskUtils
import com.dasbikash.csclientbe.utils.DateUtils
import kotlinx.coroutines.delay
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service

@Service
class RegistererToCsService(
        private var userRepository: UserRepository?=null,
        private var adminTaskService: AdminTaskService?=null
):CommandLineRunner{
    private val registerTask: ()->Unit = {
        userRepository!!.getAllByRegisteredToCs(false)
                .asSequence()
                .forEach {
                    registerUser(it.getUserRegisterRequest())
                    it.registeredToCs = true
                    userRepository!!.save(it)
                }
        Thread.sleep(USER_REG_LOP_REGULAR_SLEEP_TIME)
    }
    override fun run(vararg args: String?) {
        BgTaskUtils.launchContinuousBgTask(registerTask,PENDING_REG_SYNC_TASK_NAME)
    }

    fun registerUser(csUserRegisterRequest: CsUserRegisterRequest){
        adminTaskService!!.registerUser(csUserRegisterRequest)
    }

    companion object{
        private val USER_REG_LOP_REGULAR_SLEEP_TIME = DateUtils.MINUTE_IN_MS
        private const val PENDING_REG_SYNC_TASK_NAME = "Pending user registration on Chat Service"
    }
}