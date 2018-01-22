package com.serte.backgroundfetch

/**
 * Created by s.venturini on 20/01/2018.
 */
enum class StatusType {
    NO_ERRORS,
    GENERIC_ERROR,
    INTERNET_CONNECTION_FAILED,
    DATABASE_CONNECTION_FAILED,
    REMOTE_DATA_FETCH_FAILED,
    LOCAL_DATA_FETCH_FAILED,
    NO_LOCAL_DATA,
    NO_REMOTE_DATA
}