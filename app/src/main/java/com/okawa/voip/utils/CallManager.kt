package com.okawa.voip.utils

import android.content.Context
import android.content.Intent
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
        return Intent(context, OnBoardingActivity::class.java)
    }

}