package com.okawa.voip.ui.details

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import android.widget.Toast
import com.okawa.voip.R
import com.okawa.voip.databinding.ActivityContactDetailsBinding
import com.okawa.voip.model.CountryCode
import com.okawa.voip.presenter.details.ContactDetailsPresenter
import com.okawa.voip.ui.base.BaseActivity
import com.okawa.voip.utils.adapter.CountryAdapter
import com.okawa.voip.utils.extensions.adjustCursor
import com.okawa.voip.utils.extensions.clear
import com.okawa.voip.utils.extensions.getTextString
import com.okawa.voip.utils.extensions.isEmpty
import com.okawa.voip.utils.manager.CallManager
import javax.inject.Inject

class ContactDetailsActivity : BaseActivity<ActivityContactDetailsBinding>() {

    companion object {
        private const val REQUEST_CODE_IMAGE = 0x00bb
        const val BUNDLE_CONTACTS = "contacts"
    }

    @Inject
    lateinit var contactDetailsPresenter: ContactDetailsPresenter

    @Inject
    lateinit var callManager: CallManager

    private var photo:Uri? = null

    private val countryAdapter: CountryAdapter by lazy {
        CountryAdapter(this)
    }

    private val countryCodes = ArrayList<CountryCode>()

    private val region: String
        get() {
            return countryCodes.firstOrNull {
                it.name == dataBinding.acmCreateContactCountryCodes.getTextString()
            }?.region ?: ""
        }

    private val name: String
        get() {
            return dataBinding.edtCreateContactName.getTextString()
        }

    private val phoneNumber: String
        get() {
            return dataBinding.edtCreateContactPhoneNumber.getTextString()
        }

    override fun layoutToInflate() = R.layout.activity_contact_details

    override fun doOnCreated() {
        initToolbar()
        initAddPhotoButton()
        initCountriesView()
        initSaveButton()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home) {
            finish()
            return true
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK) {
            data?.let {
                definePhoto(it.data)
            }
        }
    }

    /**
     * Initializes the toolbar
     */
    private fun initToolbar() {
        setSupportActionBar(dataBinding.tlbCreateContactToolbar)
        setTitle(R.string.contact_details_toolbar_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    private fun initAddPhotoButton() {
        dataBinding.btnCreateContactPhoto.setOnClickListener {
            startActivityForResult(callManager.images(this@ContactDetailsActivity), REQUEST_CODE_IMAGE)
        }
    }

    /**
     * Initializes the countries auto complete edit text
     */
    private fun initCountriesView() {
        contactDetailsPresenter.retrieveCountries {
            countryCodes.addAll(it)
            countryAdapter.addAll(countryCodes)
        }

        dataBinding.acmCreateContactCountryCodes.setAdapter(countryAdapter)
        dataBinding.acmCreateContactCountryCodes.threshold = 1
        dataBinding.acmCreateContactCountryCodes.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                defineSearchText()
            }
        }
    }

    /**
     * Initializes the save button
     */
    private fun initSaveButton() {
        dataBinding.btnCreateContactSave.setOnClickListener {
            defineSearchText()
            if(validateForm()) {
                contactDetailsPresenter.insertContact(name, phoneNumber, photo)
                finish()
            }
        }
    }

    /**
     * Clear the text or set the first and only item on adapter on country edit text,
     * depending on content
     */
    private fun defineSearchText() {
        if(!dataBinding.acmCreateContactCountryCodes.isEmpty()) {
            if (countryAdapter.isEmpty) {
                dataBinding.acmCreateContactCountryCodes.clear()
            } else {
                val countryCode = countryAdapter.getItem(0)
                dataBinding.acmCreateContactCountryCodes.setText(countryCode.toString())
            }
            dataBinding.acmCreateContactCountryCodes.adjustCursor()
            dataBinding.acmCreateContactCountryCodes.clearFocus()
        }
    }

    private fun definePhoto(uri: Uri) {
        photo = uri
        dataBinding.image = photo
        dataBinding.btnCreateContactPhoto.text = ""
    }

    /**
     * Validates the form by checking if the fields are empty and if it's a valid phone number
     *
     * @return true if the form is valid, otherwise returns false
     */
    private fun validateForm(): Boolean {
        if(name.isEmpty()) {
            Toast.makeText(this@ContactDetailsActivity, R.string.create_contact_error_invalid_name, Toast.LENGTH_SHORT).show()
            return false
        }

        if(region.isEmpty() || phoneNumber.isEmpty() || !contactDetailsPresenter.validatePhoneNumber(region, phoneNumber)) {
            Toast.makeText(this@ContactDetailsActivity, R.string.create_contact_error_invalid_phone, Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}