package com.serte.testbackgrounddatafetch

import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v7.app.AppCompatActivity
import com.serte.backgroundfetch.WorkHelper
import com.serte.testbackgrounddatafetch.fragments.Fragment1
import com.serte.testbackgrounddatafetch.fragments.Fragment2
import com.serte.testbackgrounddatafetch.fragments.Fragment3
import com.serte.testbackgrounddatafetch.fragments.Fragment4

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //WorkHelper.initialize(this@MainActivity)
        supportFragmentManager.beginTransaction().replace(R.id.frame1, Fragment1()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.frame2, Fragment2()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.frame3, Fragment3()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.frame4, Fragment4()).commit()

    }
}
