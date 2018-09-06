package com.okawa.voip.repository.account

import android.content.Context
import com.okawa.voip.utils.account.AccountUtils
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val accountUtils: AccountUtils) : AccountRepository {

    override fun storeAccount(context: Context, token: String, listener: () -> Unit) {
        accountUtils.storeAccount(context, token, listener)
    }

    override fun hasAccountStored(context: Context) = accountUtils.hasAccountStored(context)

}