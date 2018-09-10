package com.okawa.voip.utils.manager

import android.content.Context
import android.content.Intent
import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import com.okawa.voip.ui.contacts.ContactsFragment
import com.okawa.voip.ui.history.HistoryFragment
import com.okawa.voip.ui.main.MainActivity
import com.okawa.voip.ui.onboarding.OnBoardingActivity
import com.okawa.voip.ui.settings.SettingsFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallManager @Inject constructor() {

    /**
     * Retrieves Intent based on OnBoarding activity
     *
     * @param context used to create the intent
     *
     * @return OnBoardingActivity intent
     */
    fun onBoarding(context: Context) : Intent {
        val intent = Intent(context, OnBoardingActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        return intent
    }

    /**
     * Retrieves Intent based on Main activity
     *
     * @param context used to create the intent
     *
     * @return MainActivity intent
     */
    fun main(context: Context) : Intent {
        val intent = Intent(context, MainActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        return intent
    }

    /**
     * Replaces the fragment on the specified container ID by a Contacts Fragment
     *
     * @param fragmentManager used to begin and execute the fragment transaction
     * @param containerId view id that will be used to replace the fragment
     */
    fun contacts(fragmentManager: FragmentManager, @IdRes containerId: Int) {
        fragmentManager.beginTransaction().replace(containerId, ContactsFragment.newInstance()).commit()
    }

    /**
     * Replaces the fragment on the specified container ID by a History Fragment
     *
     * @param fragmentManager used to begin and execute the fragment transaction
     * @param containerId view id that will be used to replace the fragment
     */
    fun history(fragmentManager: FragmentManager, @IdRes containerId: Int) {
        fragmentManager.beginTransaction().replace(containerId, HistoryFragment.newInstance()).commit()
    }

    /**
     * Replaces the fragment on the specified container ID by a Settings Fragment
     *
     * @param fragmentManager used to begin and execute the fragment transaction
     * @param containerId view id that will be used to replace the fragment
     */
    fun settings(fragmentManager: FragmentManager, @IdRes containerId: Int) {
        fragmentManager.beginTransaction().replace(containerId, SettingsFragment.newInstance()).commit()
    }

}