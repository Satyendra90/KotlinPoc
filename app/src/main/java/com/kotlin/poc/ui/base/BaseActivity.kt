package com.kotlin.poc.ui.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    /**
     * Obtain layout Resources
     */
    protected abstract fun getLayoutId(): Int

    /**
     * Obtain intent parameter
     */
    protected abstract fun getIntentExtras(intent: Intent)

    /**
     * Initialization data
     */
    protected abstract fun initEventAndData(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        getIntentExtras(intent)
        initEventAndData(savedInstanceState)
    }
}
