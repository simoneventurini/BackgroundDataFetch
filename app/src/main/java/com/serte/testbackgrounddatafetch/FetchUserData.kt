package com.serte.testbackgrounddatafetch

import android.content.Context
import com.serte.backgroundfetch.BaseWorkRunnable
import com.serte.backgroundfetch.WorkResult

/**
 * Created by sventurini on 22/01/2018.
 */
class FetchUserData: BaseWorkRunnable<String>() {

    override fun execute(context: Context?, result: WorkResult<String>) {
        result.value = "hello"
    }
}