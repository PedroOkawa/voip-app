package com.okawa.voip.ui.details

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.okawa.voip.R
import com.okawa.voip.databinding.ActivityContactDetailsBinding
import com.okawa.voip.model.Contact
import com.okawa.voip.presenter.details.ContactDetailsPresenter
import com.okawa.voip.ui.base.BaseActivity
import com.okawa.voip.utils.extensions.getTextString
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

    private var contact: Contact? = null

    private var photo:Uri? = null

    private val name: String
        get() {
            return dataBinding.edtCreateContactName.getTextString()
        }

    private val phoneNumber: String
        get() {
            return dataBinding.edtCreateContactPhoneNumber.getTextString()
        }

    override fun layoutToInflate() = R.layout.activity_contact_details

    override fun doOnRestoreInstance(savedInstanceState: Bundle) {
        contact = savedInstanceState.getParcelable(BUNDLE_CONTACTS)
    }

    override fun doOnSaveInstance(outState: Bundle) {
        outState.putParcelable(BUNDLE_CONTACTS, contact)
    }

    override fun doOnCreated() {
        retrieveContact()
        initToolbar()
        initAddPhotoButton()
        defineInitialValues()
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

    private fun retrieveContact() {
        contact = intent.getParcelableExtra(BUNDLE_CONTACTS)
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
     * Initializes the save button
     */
    private fun initSaveButton() {
        dataBinding.btnCreateContactSave.setOnClickListener {
            if(validateForm()) {
                if(contact != null && contact?.id != null) {
                    contactDetailsPresenter.updateContact(contact?.id, name, photo)
                } else {
                    contactDetailsPresenter.insertContact(name, phoneNumber, photo)
                }
                finish()
            }
        }
    }

    private fun defineInitialValues() {
        if(contact != null) {
            dataBinding.edtCreateContactName.setText(contact?.name)
            contact?.photo?.let {
                definePhoto(it)
            }
        }

        val number = if(contact != null) {
            contact?.number
        } else {
            contactDetailsPresenter.generateRandomVoIPAppNumber()
        }

        dataBinding.edtCreateContactPhoneNumber.setText(number)
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

        if(phoneNumber.isEmpty()) {
            Toast.makeText(this@ContactDetailsActivity, R.string.create_contact_error_invalid_phone, Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}