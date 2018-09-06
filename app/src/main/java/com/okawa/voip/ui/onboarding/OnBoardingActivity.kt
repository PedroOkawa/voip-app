package com.okawa.voip.ui.onboarding

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.okawa.voip.R
import com.okawa.voip.databinding.ActivityOnBoardingBinding
import com.okawa.voip.model.CountryCode
import com.okawa.voip.presenter.OnBoardingPresenter
import com.okawa.voip.ui.base.BaseActivity
import com.okawa.voip.utils.adapter.CountryAdapter
import com.okawa.voip.utils.extensions.adjustCursor
import com.okawa.voip.utils.extensions.clear
import com.okawa.voip.utils.extensions.isEmpty
import javax.inject.Inject

class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {

    companion object {
        const val BUNDLE_ON_BOARDING_COUNTRY_CODE = "on_boarding_country_code"
    }

    @Inject
    lateinit var onBoardingPresenter: OnBoardingPresenter

    private var countryCode: CountryCode? = null
    private lateinit var countriesAdapter: CountryAdapter

    override fun layoutToInflate() = R.layout.activity_on_boarding

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(BUNDLE_ON_BOARDING_COUNTRY_CODE, countryCode)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        countryCode = savedInstanceState?.getParcelable(BUNDLE_ON_BOARDING_COUNTRY_CODE)
    }

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
        countriesAdapter = CountryAdapter(
                this,
                onBoardingPresenter.retrieveCountryCodes())

        dataBinding.acmOnBoardingCountryCodes.setAdapter(countriesAdapter)
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
                // TODO: REGISTER THE PHONE ON GOOGLE ACCOUNTS AND START MAIN ACTIVITY
                Log.w("TEST", "START MAIN ACTIVITY")
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
            if (countriesAdapter.isEmpty) {
                dataBinding.acmOnBoardingCountryCodes.clear()
            } else {
                countryCode = countriesAdapter.getItem(0)
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
        if(dataBinding.acmOnBoardingCountryCodes.isEmpty() ||
                dataBinding.edtOnBoardingPhoneNumber.isEmpty() ||
                countryCode == null) {
            return false
        }

        return onBoardingPresenter
                .validatePhoneNumber(countryCode!!.region,
                        dataBinding.edtOnBoardingPhoneNumber.text.toString())

    }
}