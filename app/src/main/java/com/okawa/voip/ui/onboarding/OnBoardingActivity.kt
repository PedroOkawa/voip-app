package com.okawa.voip.ui.onboarding

import com.okawa.voip.R
import com.okawa.voip.databinding.ActivityOnBoardingBinding
import com.okawa.voip.presenter.OnBoardingPresenter
import com.okawa.voip.ui.base.BaseActivity
import javax.inject.Inject

class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {

    @Inject
    lateinit var onBoardingPresenter: OnBoardingPresenter

    override fun layoutToInflate() = R.layout.activity_on_boarding

    override fun doOnCreated() {
        onBoardingPresenter.retrieveCountryCodes()
    }
}