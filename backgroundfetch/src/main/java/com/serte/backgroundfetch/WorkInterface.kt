package com.serte.backgroundfetch

/**
 * Created by sventurini on 23/02/2018.
 */
interface WorkInterface {

    fun execute(workRunnable: BaseWorkRunnable<*>, callback: Callback<*>): WorkHelper

    fun execute(workRunnable: BaseWorkRunnable<*>, delay: Long, callback: Callback<*>): WorkHelper

    fun execute(workRunnable: BaseWorkRunnable<*>, tag: String, callback: Callback<*>): WorkHelper

    fun execute(workRunnable: BaseWorkRunnable<*>, eventTag: String, delay: Long, callback: Callback<*>): WorkHelper
}