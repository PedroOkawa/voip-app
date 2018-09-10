package com.okawa.voip.ui.create

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import android.widget.Toast
import com.okawa.voip.R
import com.okawa.voip.databinding.ActivityCreateContactBinding
import com.okawa.voip.presenter.create.CreateContactPresenter
import com.okawa.voip.ui.base.BaseActivity
import com.okawa.voip.utils.adapter.CountryAdapter
import com.okawa.voip.utils.extensions.adjustCursor
import com.okawa.voip.utils.extensions.clear
import com.okawa.voip.utils.extensions.getTextString
import com.okawa.voip.utils.extensions.isEmpty
import com.okawa.voip.utils.manager.CallManager
import javax.inject.Inject

class CreateContactActivity : BaseActivity<ActivityCreateContactBinding>() {

    companion object {
        private const val REQUEST_CODE_IMAGE = 0x00bb
    }

    @Inject
    lateinit var createContactPresenter: CreateContactPresenter

    @Inject
    lateinit var callManager: CallManager

    private var photo:Uri? = null

    private val region: String
        get() {
            return createContactPresenter.retrieveRegion(dataBinding.acmCreateContactCountryCodes.getTextString())
        }

    private val name: String
        get() {
            return dataBinding.edtCreateContactName.getTextString()
        }

    private val phoneNumber: String
        get() {
            return dataBinding.edtCreateContactPhoneNumber.getTextString()
        }

    private val countryAdapter: CountryAdapter by lazy {
        CountryAdapter(this, createContactPresenter.retrieveCountries())
    }

    override fun layoutToInflate() = R.layout.activity_create_contact

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
        setTitle(R.string.create_contact_toolbar_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    private fun initAddPhotoButton() {
        dataBinding.btnCreateContactPhoto.setOnClickListener {
            startActivityForResult(callManager.images(this@CreateContactActivity), REQUEST_CODE_IMAGE)
        }
    }

    /**
     * Initializes the countries auto complete edit text
     */
    private fun initCountriesView() {
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
                createContactPresenter.insertContact(name, phoneNumber, photo)
                finish()
            } else {
                Toast.makeText(this@CreateContactActivity, R.string.on_boarding_error_invalid_phone, Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this@CreateContactActivity, R.string.create_contact_error_invalid_name, Toast.LENGTH_SHORT).show()
            return false
        }

        if(region.isEmpty() || phoneNumber.isEmpty() || !createContactPresenter.validatePhoneNumber(region, phoneNumber)) {
            Toast.makeText(this@CreateContactActivity, R.string.create_contact_error_invalid_phone, Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}