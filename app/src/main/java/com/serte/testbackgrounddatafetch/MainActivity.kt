package com.serte.testbackgrounddatafetch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.serte.backgroundfetch.Callback
import com.serte.backgroundfetch.WorkHelperActivity
import com.serte.backgroundfetch.WorkResult
import technogym.testmultithread.WorkThread1
import technogym.testmultithread.WorkThread2
import technogym.testmultithread.WorkThread3
import technogym.testmultithread.WorkThread4

class MainActivity : AppCompatActivity() {

    private lateinit var helper: WorkHelperActivity

    companion object {
        const val TAG_ID = "YOUR_TAG_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helper = WorkHelperActivity(this@MainActivity)
        helper.setNThreads(4) //optional. Default value = 4
        helper.execute(FetchUserData(), TAG_ID, Callback<String> { result ->
            Log.d("WorkHelper", "TAG= " + result.tag + " Result=" + result.value)
        })
    }

    fun onClickStart(view: View) {
        if (!helper.isEnqueued("WorkThread1"))
            helper.execute(WorkThread1(), "WorkThread1", Callback<Int> { result ->
                Log.d("WorkHelper", "TH: " + result.value)
            })

        helper.execute(WorkThread2(), Response())
        helper.execute(WorkThread3(), Response())
        helper.execute(WorkThread4(), Response())
    }

    inner class Response : Callback<Int> {
        override fun dataLoaded(result: WorkResult<Int>) {
            Log.d("WorkHelper", "TH: " + result.value)
        }
    }
}
