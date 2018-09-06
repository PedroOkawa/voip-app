package com.okawa.voip.ui.main

import com.okawa.voip.R
import com.okawa.voip.databinding.ActivityMainBinding
import com.okawa.voip.ui.base.BaseActivity
import com.okawa.voip.utils.manager.CallManager
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var callManager: CallManager

    override fun layoutToInflate() = R.layout.activity_main

    override fun doOnCreated() {
        initToolbar()
        inflateInitialFragment()
    }

    /**
     * Initializes the toolbar
     */
    private fun initToolbar() {
        setSupportActionBar(dataBinding.tlbMainToolbar)
        setTitle(R.string.main_toolbar_title)
    }

    private fun inflateInitialFragment() {
        callManager.contacts(supportFragmentManager, R.id.frmMainContent)
    }
}