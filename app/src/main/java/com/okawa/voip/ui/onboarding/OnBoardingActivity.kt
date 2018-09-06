package com.okawa.voip.ui.onboarding

import android.widget.Toast
import com.okawa.voip.R
import com.okawa.voip.databinding.ActivityOnBoardingBinding
import com.okawa.voip.presenter.onboarding.OnBoardingPresenter
import com.okawa.voip.ui.base.BaseActivity
import com.okawa.voip.utils.adapter.CountryAdapter
import com.okawa.voip.utils.extensions.adjustCursor
import com.okawa.voip.utils.extensions.clear
import com.okawa.voip.utils.extensions.getTextString
import com.okawa.voip.utils.extensions.isEmpty
import com.okawa.voip.utils.manager.CallManager
import javax.inject.Inject

class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {

    @Inject
    lateinit var onBoardingPresenter: OnBoardingPresenter

    @Inject
    lateinit var callManager: CallManager

    private val region: String
        get() {
            return onBoardingPresenter.retrieveRegion(dataBinding.acmOnBoardingCountryCodes.getTextString())
        }

    private val phoneNumber: String
        get() {
            return dataBinding.edtOnBoardingPhoneNumber.getTextString()
        }

    private val countryAdapter: CountryAdapter by lazy {
        CountryAdapter(this, onBoardingPresenter.retrieveCountries())
    }

    override fun layoutToInflate() = R.layout.activity_on_boarding

    override fun doOnCreated() {
        initToolbar()
        initCountriesView()
        initValidateButton()
    }

    /**
     * Initializes the toolbar
     */
    private fun initToolbar() {
        setSupportActionBar(dataBinding.tlbOnBoardingToolbar)
        setTitle(R.string.on_boarding_phone_validation_toolbar_title)
    }

    /**
     * Initializes the countries auto complete edit text
     */
    private fun initCountriesView() {
        dataBinding.acmOnBoardingCountryCodes.setAdapter(countryAdapter)
        dataBinding.acmOnBoardingCountryCodes.threshold = 1
        dataBinding.acmOnBoardingCountryCodes.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                defineSearchText()
            }
        }
    }

    /**
     * Initializes the validate button
     */
    private fun initValidateButton() {
        dataBinding.btnOnBoardingValidate.setOnClickListener {
            defineSearchText()
            if(validateForm()) {
                onBoardingPresenter.storeAccount(this@OnBoardingActivity, phoneNumber) {
                    startActivity(callManager.main(this@OnBoardingActivity))
                }
            } else {
                Toast.makeText(this@OnBoardingActivity, R.string.on_boarding_error_invalid_phone, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Clear the text or set the first and only item on adapter on country edit text,
     * depending on content
     */
    private fun defineSearchText() {
        if(!dataBinding.acmOnBoardingCountryCodes.isEmpty()) {
            if (countryAdapter.isEmpty) {
                dataBinding.acmOnBoardingCountryCodes.clear()
            } else {
                val countryCode = countryAdapter.getItem(0)
                dataBinding.acmOnBoardingCountryCodes.setText(countryCode.toString())
            }
            dataBinding.acmOnBoardingCountryCodes.adjustCursor()
            dataBinding.acmOnBoardingCountryCodes.clearFocus()
        }
    }

    /**
     * Validates the form by checking if the fields are empty and if it's a valid phone number
     *
     * @return true if the form is valid, otherwise returns false
     */
    private fun validateForm(): Boolean {
        if(region.isEmpty() || phoneNumber.isEmpty()) {
            return false
        }

        return onBoardingPresenter.validatePhoneNumber(region, phoneNumber)

    }
}