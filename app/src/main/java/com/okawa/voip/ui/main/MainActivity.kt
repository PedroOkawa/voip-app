package com.okawa.voip.ui.main

import android.support.annotation.IdRes
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
        initBottomNavigation()
        inflateFragment()
    }

    /**
     * Initializes the toolbar
     */
    private fun initToolbar() {
        setSupportActionBar(dataBinding.tlbMainToolbar)
        setTitle(R.string.main_toolbar_title)
    }

    private fun initBottomNavigation() {
        dataBinding.btmMainNavigation.setOnNavigationItemSelectedListener { menuItem ->
            inflateFragment(menuItem.itemId)
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun inflateFragment(@IdRes menuItemId: Int = R.id.action_contacts) {
        when (menuItemId) {
            R.id.action_contacts -> callManager.contacts(supportFragmentManager, R.id.frmMainContent)
            R.id.action_history -> callManager.history(supportFragmentManager, R.id.frmMainContent)
            R.id.action_settings -> callManager.settings(supportFragmentManager, R.id.frmMainContent)
        }
    }
}