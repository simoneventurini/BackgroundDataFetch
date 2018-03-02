package com.serte.backgroundfetch

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.runOnUiThread
import java.util.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by s.venturini on 20/01/2018.
 */
open class WorkHelper (val mContext: Context) : WorkInterface {


    companion object {

        private val runnableMap = HashMap<String, ArrayList<BaseWorkRunnable<*>>>()
        fun notifyEvent(eventTag: String) {
            //val value = runnableMap[eventTag]?.get(0)?.get()

            runnableMap[eventTag].let { it -> it?.forEach { runnable -> runnable.start() }}
        }
    }

    private val sThreadFactory = object : ThreadFactory {
        private val mCount = AtomicInteger(1)

        override fun newThread(r: Runnable): Thread {
            return Thread(r, "ModernAsyncTask #" + mCount.getAndIncrement())
        }
    }
    private val threadPoolExecutor: Executor = ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>(10), sThreadFactory)
    private var executor = threadPoolExecutor
    private val mController = Controller()
    private var isPause = false
    private val enqueuedMap = HashMap<String, Boolean>()
    private val dispacthMap = LinkedHashMap<Callback<Any>, WorkResult<Any>>() //Ordered Hashmap for a correctly dispatchement
    //private val observerMap = HashMap<String, Observer>()

    override fun execute(workRunnable: BaseWorkRunnable<*>, callback: Callback<*>): WorkHelper {
        return execute(workRunnable, "", 0, callback)
    }

    override fun execute(workRunnable: BaseWorkRunnable<*>, delay: Long, callback: Callback<*>): WorkHelper {
        return execute(workRunnable, "", delay, callback)
    }

    override fun execute(workRunnable: BaseWorkRunnable<*>, tag: String, callback: Callback<*>): WorkHelper {
        return execute(workRunnable, tag, 0, callback)
    }

    override fun execute(workRunnable: BaseWorkRunnable<*>, eventTag: String, delay: Long, callback: Callback<*>): WorkHelper {
        workRunnable.setContext(mContext)
        workRunnable.setController(mController)
        workRunnable.setCallback(callback)
        workRunnable.setTag(eventTag)
        workRunnable.setDelay(delay)
        enqueuedMap[eventTag] = true
        workRunnable.start()
        if (runnableMap.containsKey(eventTag)){
            val listRunnable = runnableMap[eventTag]
            listRunnable?.add(workRunnable)

        } else {
            val listRunnable = ArrayList<BaseWorkRunnable<*>>()
            listRunnable.add(workRunnable)
            runnableMap[eventTag] = listRunnable

        }
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
        executor = Executors.newFixedThreadPool(value)
    }

    fun isEnqueued(eventTag: String): Boolean {
        return enqueuedMap.containsKey(eventTag)
    }

    internal interface ControllerWork {
        fun onFinishLoad(result: WorkResult<Any>, callback: Callback<Any>)
    }

    inner class Controller : ControllerWork {

        @Synchronized
        override fun onFinishLoad(result: WorkResult<Any>, callback: Callback<Any>) {
            dispacthMap.put(callback, result)
            dispatchResult()
        }

        fun getExecutorService(): Executor {
            return executor
        }
    }

    @Subscribe
    fun dispatchResult() {
        if (isPause) return
        for (callback in dispacthMap.keys) {
            val result = dispacthMap[callback]
            if (result != null) {
                mContext.runOnUiThread {
                    callback.dataLoaded(result)
                }
                enqueuedMap.remove(result.eventTag)
            }
        }
        dispacthMap.clear()
    }
}