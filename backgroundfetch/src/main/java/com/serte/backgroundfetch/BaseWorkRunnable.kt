package com.serte.backgroundfetch

import android.content.Context
import org.jetbrains.anko.doAsync

/**
 * Created by s.venturini on 20/01/2018.
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseWorkRunnable<T>: Runnable {

    fun setController(controller: WorkHelper.Controller) {
        mController = controller
    }

    fun setCallback(callback: Callback<*>) {
        this.weakCallback = callback
    }

    private lateinit var mController: WorkHelper.Controller
    private var weakCallback: Callback<*>? = null
    private var context: Context? = null
    private var delay = 0L

    var result: WorkResult<T> = WorkResult()

    fun start() {
        context.doAsync {
            Thread.sleep(delay)
            mController.getExecutorService().execute(this@BaseWorkRunnable)
        }
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun setTag(tag: String) {
        result.eventTag = tag
    }

    fun setDelay(delay: Long) {
        this.delay = delay
    }

    override fun run() {
        execute(context, result)
        dispatchResult()
    }

    protected abstract fun execute(context: Context?, result: WorkResult<T>)

    private fun dispatchResult() {
        mController.onFinishLoad(result as WorkResult<Any>, weakCallback as Callback<Any>)
    }
}