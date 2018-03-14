package com.serte.backgroundfetch

import android.content.Context
import org.jetbrains.anko.doAsync
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

/**
 * Created by s.venturini on 20/01/2018.
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseWorkRunnable<T>: Runnable {

    fun setController(controller: WorkHelper.Controller) {
        mController = controller
    }

    fun setCallback(callback: Callback<*>) {
        this.callback = callback
    }

    private var mController: WorkHelper.Controller? = null
    private var future: Future<*>? = null
    private var callback: Callback<*>? = null
    private var context: Context? = null

    private var result: WorkResult<T> = WorkResult()

    fun start(service: ExecutorService) {
        future = service.submit(this)
    }

    fun start(service: ExecutorService, delay: Long) {
        val runnable = this
        doAsync {
            Thread.sleep(delay)
            future = service.submit(runnable)
        }

    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun setTag(tag: String) {
        result.tag = tag
    }

    override fun run() {
        execute(context, result)
        dispatchResult()
    }

    protected fun onDestroy() {
        if (future != null && !future!!.isCancelled) future!!.cancel(true)
    }

    protected abstract fun execute(context: Context?, result: WorkResult<T>)

    private fun dispatchResult() {
        if (mController != null) {
            mController!!.onFinishLoad(result as WorkResult<Any>, (callback as Callback<Any>?)!!)
        }
    }
}