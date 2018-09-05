package com.okawa.voip.ui.onboarding

import android.widget.ArrayAdapter
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
        initCountriesView()
    }

    private fun initCountriesView() {
        val countries = onBoardingPresenter.retrieveCountries()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries)

        dataBinding.acmOnBoardingCountryCodes.setAdapter(adapter)
        dataBinding.acmOnBoardingCountryCodes.threshold = 1
        dataBinding.acmOnBoardingCountryCodes.setOnDismissListener {
            if(!dataBinding.acmOnBoardingCountryCodes.text.isEmpty()) {
                dataBinding.acmOnBoardingCountryCodes.setText(if (adapter.isEmpty) "" else adapter.getItem(0))
                dataBinding.acmOnBoardingCountryCodes.clearFocus()
            }
        }
    }
}