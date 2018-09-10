package com.okawa.voip.ui.main

import android.os.Bundle
import android.support.annotation.IdRes
import com.okawa.voip.R
import com.okawa.voip.databinding.ActivityMainBinding
import com.okawa.voip.ui.base.BaseActivity
import com.okawa.voip.utils.manager.CallManager
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        const val BUNDLE_SELECTED_ITEM_ID = "selected_item_id"
    }

    @Inject
    lateinit var callManager: CallManager

    private var selectedItemId: Int? = null

    override fun layoutToInflate() = R.layout.activity_main

    override fun doOnCreated() {
        initToolbar()
        initBottomNavigation()
    }

    override fun doOnRestoreInstance(savedInstanceState: Bundle) {
        selectedItemId = savedInstanceState.getInt(BUNDLE_SELECTED_ITEM_ID)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(BUNDLE_SELECTED_ITEM_ID, dataBinding.btmMainNavigation.selectedItemId)
    }

    /**
     * Initializes the toolbar
     */
    private fun initToolbar() {
        setSupportActionBar(dataBinding.tlbMainToolbar)
        setTitle(R.string.main_toolbar_title)
    }

    /**
     * Initializes the bottom navigation view
     */
    private fun initBottomNavigation() {
        dataBinding.btmMainNavigation.setOnNavigationItemSelectedListener { menuItem ->
            inflateFragment(menuItem.itemId)
        }
        dataBinding.btmMainNavigation.setOnNavigationItemReselectedListener{ _ ->

        }
        if(selectedItemId != null) {
            dataBinding.btmMainNavigation.selectedItemId = selectedItemId ?: R.id.action_contacts
        } else {
            inflateFragment()
        }
    }

    /**
     * Inflates the fragment depending on menu selected
     *
     * @param menuItemId id of the menu selected
     *
     * @return true if inflates any fragment, otherwise false
     */
    private fun inflateFragment(@IdRes menuItemId: Int = R.id.action_contacts): Boolean {
        return when (menuItemId) {
            R.id.action_contacts -> {
                callManager.contacts(supportFragmentManager, R.id.frmMainContent)
                return true
            }
            R.id.action_history -> {
                callManager.history(supportFragmentManager, R.id.frmMainContent)
                return true
            }
            R.id.action_settings -> {
                callManager.settings(supportFragmentManager, R.id.frmMainContent)
                return true
            }
            else -> false
        }
    }
}