package com.serte.testbackgrounddatafetch

import android.content.Context
import com.serte.backgroundfetch.WorkHelper

/**
 * Created by sventurini on 15/02/2018.
 */
class FakeDataBase {

    companion object {
        var messageUser = ""
    }

    fun updateMessageUser(context: Context?, value: String) {
        messageUser = value
        if (context == null) return
        WorkHelper.notifyEvent(Events.EVENT_TAG_MESSAGE_USER_UPDATED)
    }
}