package com.okawa.voip.utils.manager

import android.content.Context
import android.content.Intent
import com.okawa.voip.ui.main.MainActivity
import com.okawa.voip.ui.onboarding.OnBoardingActivity
import javax.inject.Inject

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

}