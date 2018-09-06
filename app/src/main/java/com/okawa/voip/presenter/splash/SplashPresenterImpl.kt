package com.okawa.voip.presenter.splash

import android.content.Context
import com.okawa.voip.repository.account.AccountRepository
import javax.inject.Inject

class SplashPresenterImpl @Inject constructor(private val accountRepository: AccountRepository) : SplashPresenter {

    override fun hasAccountStored(context: Context) = accountRepository.hasAccountStored(context)

}