package com.okawa.voip.ui.splash

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.okawa.voip.R
import com.okawa.voip.databinding.ActivitySplashBinding
import com.okawa.voip.presenter.splash.SplashPresenter
import com.okawa.voip.ui.base.BaseActivity
import com.okawa.voip.utils.utils.PermissionUtils
import com.okawa.voip.utils.manager.CallManager
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    companion object {
        const val SPLASH_DELAY = 1000L
        const val REQUEST_CODE_CONTACTS_PERMISSIONS = 0x000a
    }

    @Inject
    lateinit var splashPresenter: SplashPresenter

    @Inject
    lateinit var callManager: CallManager

    @Inject
    lateinit var permissionUtils: PermissionUtils

    override fun layoutToInflate() = R.layout.activity_splash

    override fun doOnCreated() {
        if(permissionUtils.checkPermissions(this, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG)) {
            openNextActivity()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG), REQUEST_CODE_CONTACTS_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_CODE_CONTACTS_PERMISSIONS) {
            if(grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                openNextActivity()
            }
        }
    }

    private fun openNextActivity() {
        Timer().schedule(SPLASH_DELAY) {
            if(splashPresenter.hasAccountStored(this@SplashActivity)) {
                startActivity(callManager.main(this@SplashActivity))
            } else {
                startActivity(callManager.onBoarding(this@SplashActivity))
            }
        }
    }
}