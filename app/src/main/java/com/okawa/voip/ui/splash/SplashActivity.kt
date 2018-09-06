package com.okawa.voip.ui.splash

import com.okawa.voip.R
import com.okawa.voip.databinding.ActivitySplashBinding
import com.okawa.voip.presenter.splash.SplashPresenter
import com.okawa.voip.ui.base.BaseActivity
import com.okawa.voip.utils.manager.CallManager
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    companion object {
        const val SPLASH_DELAY = 1000L
    }

    @Inject
    lateinit var splashPresenter: SplashPresenter

    @Inject
    lateinit var callManager: CallManager

    override fun layoutToInflate() = R.layout.activity_splash

    override fun doOnCreated() {
        Timer().schedule(SPLASH_DELAY) {
            if(splashPresenter.hasAccountStored(this@SplashActivity)) {
                startActivity(callManager.main(this@SplashActivity))
            } else {
                startActivity(callManager.onBoarding(this@SplashActivity))
            }
        }
    }
}