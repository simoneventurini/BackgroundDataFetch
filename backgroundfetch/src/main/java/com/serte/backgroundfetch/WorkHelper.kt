package com.serte.backgroundfetch

import android.content.Context
import org.jetbrains.anko.runOnUiThread
import java.util.*
import java.util.concurrent.Executors

/**
 * Created by s.venturini on 20/01/2018.
 */
open class WorkHelper(context: Context) {

    private var executorService = Executors.newFixedThreadPool(4)
    private val mController = Controller()
    private var isPause = false
    private val enqueuedMap = HashMap<String, Boolean>()
    private val dispacthMap = LinkedHashMap<Callback<Any>, WorkResult<Any>>() //Ordered Hashmap for a correctly dispatchement
    private val mContext: Context = context.applicationContext

    fun execute(workRunnable: BaseWorkRunnable<*>, callback: Callback<*>): WorkHelper {
        return execute(workRunnable, "", 0, callback)
    }

    fun execute(workRunnable: BaseWorkRunnable<*>, delay: Long, callback: Callback<*>): WorkHelper {
        return execute(workRunnable, "", delay, callback)
    }

    fun execute(workRunnable: BaseWorkRunnable<*>, tag: String, callback: Callback<*>): WorkHelper {
        return execute(workRunnable, tag, 0, callback)
    }

    fun execute(workRunnable: BaseWorkRunnable<*>, tag: String, delay: Long, callback: Callback<*>): WorkHelper {
        workRunnable.setContext(mContext)
        workRunnable.setController(mController)
        workRunnable.setCallback(callback)
        workRunnable.setTag(tag)
        workRunnable.start(executorService, delay)
        enqueuedMap[tag] = true
        return this
    }

    open fun onPause() {
        isPause = true
    }

    open fun onResume() {
        isPause = false
        dispatchResult()
    }

    fun setNThreads(value: Int) {
        executorService = Executors.newFixedThreadPool(value)
    }

    fun isEnqueued(tag: String): Boolean {
        return enqueuedMap.containsKey(tag)
    }

    internal interface ControllerWork {
        fun onFinishLoad(result: WorkResult<Any>, callback: Callback<Any>?)
    }

    inner class Controller : ControllerWork {

        @Synchronized
        override fun onFinishLoad(result: WorkResult<Any>, callback: Callback<Any>?) {
            callback?.let { dispacthMap.put(it, result) }
            dispatchResult()
        }
    }

    fun dispatchResult() {
        if (isPause) return
        for (callback in dispacthMap.keys) {
            val result = dispacthMap[callback]
            if (result != null) {
                mContext.runOnUiThread {
                    callback.dataLoaded(result)
                }
                enqueuedMap.remove(result.tag)
            }
        }
        dispacthMap.clear()
    }
}