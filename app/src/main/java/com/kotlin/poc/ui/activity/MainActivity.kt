package com.kotlin.poc.ui.activity

import android.content.Intent
import android.os.Bundle
import com.kotlin.poc.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getIntentExtras(intent: Intent) {

    }

    override fun initEventAndData(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
    }
}
