package com.serte.testbackgrounddatafetch.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.serte.backgroundfetch.Callback
import com.serte.backgroundfetch.WorkHelper
import com.serte.testbackgrounddatafetch.Events
import com.serte.testbackgrounddatafetch.FakeDataBase
import com.serte.testbackgrounddatafetch.R
import kotlinx.android.synthetic.main.fragment.*
import technogym.testmultithread.WorkThread2

/**
 * Created by sventurini on 15/02/2018.
 */
class Fragment2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment, container, false)

        view.findViewById<TextView>(R.id.txtPosition).text = "2"
        view.findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            FakeDataBase().updateMessageUser(activity?.baseContext!!, edtText.text.toString())
        }

        val edtText = view.findViewById<EditText>(R.id.edtText)
        WorkHelper(context!!).execute(WorkThread2(), Events.EVENT_TAG_MESSAGE_USER_UPDATED,
                Callback<String> { result ->
                    edtText.setText(result.value)
                })

        return view!!
    }
}