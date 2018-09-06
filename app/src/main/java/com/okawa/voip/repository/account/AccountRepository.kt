package com.okawa.voip.repository.account

import android.content.Context

interface AccountRepository {

    fun storeAccount(context: Context, token: String, listener: () -> Unit)

    fun hasAccountStored(context: Context): Boolean

}