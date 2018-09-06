package com.okawa.voip.utils.account

import android.app.Service
import android.content.Intent

class AuthenticatorService : Service() {

    override fun onBind(intent: Intent?) = AccountAuthenticator(this).iBinder

}