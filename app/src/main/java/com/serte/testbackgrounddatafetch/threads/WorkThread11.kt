package com.serte.testbackgrounddatafetch.threads

import android.content.Context
import android.util.Log
import com.serte.backgroundfetch.BaseWorkRunnable
import com.serte.backgroundfetch.StatusType
import com.serte.backgroundfetch.WorkResult
import com.serte.testbackgrounddatafetch.FakeDataBase

/**
 * Created by sventurini on 21/01/2018.
 */
class WorkThread11 : BaseWorkRunnable<String>() {

    override fun execute(context: Context?, result: WorkResult<String>){
        result.value = FakeDataBase.messageUser
        result.status.message = ""
        result.status.type = StatusType.NO_ERRORS
        Log.d("backgroundDataFetch", "Thread11 - id = " + Thread.currentThread().id)
    }
}