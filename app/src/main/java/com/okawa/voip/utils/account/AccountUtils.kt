package com.okawa.voip.utils.account

import android.accounts.Account
import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import com.okawa.voip.R
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AccountUtils @Inject constructor() {

    private val removeSubject = PublishSubject.create<Boolean>()

    /**
     * Stores the account on account manager
     *
     * @param context used to retrieve the account manager
     * @param token authentication token
     * @param listener to be invoked when account is added
     */
    @SuppressLint("CheckResult")
    fun storeAccount(context: Context, token: String, listener: () -> Unit) {
        val accountManager = AccountManager.get(context)
        removeSubject.subscribe {
            val account = Account(context.getString(R.string.app_name), context.packageName)
            accountManager.addAccountExplicitly(account, "", null)
            accountManager.setAuthToken(account, context.packageName, token)
            listener.invoke()
        }
        clearAccounts(context)
    }

    /**
     * Validates if there is an account stored
     *
     * @param context used to retrieve the account manager
     *
     * @return true if the user has an account stored, otherwise false
     */
    fun hasAccountStored(context: Context) = retrieveAccount(context) != null

    /**
     * Retrieves the account by type
     *
     * @param context used to retrieve the account manager
     *
     * @return Account stored
     */
    private fun retrieveAccount(context: Context): Account? {
        val accountManager = AccountManager.get(context)
        return accountManager.getAccountsByType(context.packageName).firstOrNull()
    }

    /**
     * Remove all accounts
     *
     * @param context used to retrieve the account manager
     */
    private fun clearAccounts(context: Context) {
        val accountManager = AccountManager.get(context)
        val accounts = accountManager.getAccountsByType(context.packageName)
        if(accounts.isEmpty()) {
            removeSubject.onNext(true)
            return
        }
        accounts.forEach { account ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                accountManager.removeAccount(account, null, {
                    validateAccountDeletion(context)
                }, null)
            } else {
                accountManager.removeAccount(account, {
                    validateAccountDeletion(context)
                }, null)
            }
        }
    }

    /**
     * Validates the account deletion and publishes the result
     *
     * @param context used to retrieve the account manager
     */
    private fun validateAccountDeletion(context: Context) {
        val accountManager = AccountManager.get(context)
        if(accountManager.accounts.isEmpty()) {
            removeSubject.onNext(true)
        }
    }

}