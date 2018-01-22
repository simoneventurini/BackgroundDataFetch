package com.serte.testbackgrounddatafetch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.serte.backgroundfetch.Callback
import com.serte.backgroundfetch.WorkHelper
import com.serte.backgroundfetch.WorkResult
import technogym.testmultithread.WorkThread1
import technogym.testmultithread.WorkThread2
import technogym.testmultithread.WorkThread3
import technogym.testmultithread.WorkThread4

class MainActivity : AppCompatActivity() {

    private lateinit var helper: WorkHelper

    companion object {
        const val TAG = "TESTMULTITHREAD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helper = WorkHelper(this@MainActivity)
        helper.execute(FetchUserData(), Callback<String> {
            result -> Log.d(TAG, "TH: " + result.value)
        })
    }

    fun onClickStart(view: View) {
        helper.execute(WorkThread1(), Callback<Int> {
            result -> Log.d(TAG, "TH: " + result.value)
        })

        helper.execute(WorkThread2(), Response())
        helper.execute(WorkThread3(), Response())
        helper.execute(WorkThread4(), Response())
    }

    override fun onResume() {
        super.onResume()
        helper.onResume()
    }

    override fun onPause() {
        super.onPause()
        helper.onPause()
    }

    inner class Response: Callback<Int> {
        override fun dataLoaded(result: WorkResult<Int>) {
            Log.d(TAG, "TH: " + result.value)
        }
    }
}
