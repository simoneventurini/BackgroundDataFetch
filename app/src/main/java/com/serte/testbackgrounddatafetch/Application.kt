package com.serte.testbackgrounddatafetch

import android.app.Application
import com.serte.backgroundfetch.WorkHelper

/**
 * Created by sventurini on 22/02/2018.
 */
class Application: Application() {

    override fun onCreate() {
        super.onCreate()
     //   WorkHelper.initialize(this@Application)
    }
}