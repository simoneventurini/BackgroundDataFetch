package com.serte.backgroundfetch

/**
 * Created by s.venturini on 20/01/2018.
 */
class WorkResult<T> {

    var tag = ""
    var status =  Status()
    var value: T? = null

    inner class Status {
        var type: StatusType = StatusType.NO_ERRORS
        var message: String  = ""
    }
}