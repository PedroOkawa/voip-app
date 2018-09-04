package com.okawa.voip.utils

import android.content.Context
import android.content.Intent
import com.okawa.voip.ui.onboarding.OnBoardingActivity
import javax.inject.Inject

class CallManager @Inject constructor() {

    fun onBoarding(context: Context) : Intent {
        return Intent(context, OnBoardingActivity::class.java)
    }

}