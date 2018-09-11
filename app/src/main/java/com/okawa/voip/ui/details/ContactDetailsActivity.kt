package com.okawa.voip.ui.details

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.okawa.voip.R
import com.okawa.voip.databinding.ActivityContactDetailsBinding
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

    private val name: String
        get() {
            return dataBinding.edtCreateContactName.getTextString()
        }

    private val phoneNumber: String
        get() {
            return dataBinding.txtCreateContactPhoneNumber.getTextString()
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(contactDetailsPresenter.isEditMode()) {
            menuInflater.inflate(R.menu.menu_contact_details, menu)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home) {
            finish()
            return true
        } else if (item?.itemId == R.id.action_delete_contact) {
            callManager.deleteContactConfirmation(this) {
                contactDetailsPresenter.deleteContact()
                finish()
            }
            return true
        }
        return false
    }

    override fun layoutToInflate() = R.layout.activity_contact_details

    override fun doOnRestoreInstance(savedInstanceState: Bundle) {
        contactDetailsPresenter.defineContact(savedInstanceState.getParcelable(BUNDLE_CONTACTS))
    }

    override fun doOnSaveInstance(outState: Bundle) {
        outState.putParcelable(BUNDLE_CONTACTS, contactDetailsPresenter.getContact())
    }

    override fun doOnCreated() {
        retrieveContact()
        initToolbar()
        initAddPhotoButton()
        defineInitialValues()
        initSaveButton()
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
     * Retrieve contact parcelable
     */
    private fun retrieveContact() {
        contactDetailsPresenter.defineContact(intent.getParcelableExtra(BUNDLE_CONTACTS))
    }

    /**
     * Initializes the toolbar
     */
    private fun initToolbar() {
        val title = if(contactDetailsPresenter.isEditMode())
            R.string.contact_details_edit_toolbar_title
        else
            R.string.contact_details_add_toolbar_title

        setSupportActionBar(dataBinding.tlbCreateContactToolbar)
        setTitle(title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    /**
     * Initializes the add photo button action
     */
    private fun initAddPhotoButton() {
        dataBinding.imbCreateContactPhoto.setOnClickListener {
            startActivityForResult(callManager.images(this@ContactDetailsActivity), REQUEST_CODE_IMAGE)
        }
    }

    /**
     * Defines the photo uri to be saved
     *
     * @param uri photo
     */
    private fun definePhoto(uri: Uri) {
        contactDetailsPresenter.definePhoto(uri)
        dataBinding.image = uri
    }

    /**
     * Initializes the save button
     */
    private fun initSaveButton() {
        dataBinding.btnCreateContactSave.setOnClickListener {
            if(validateForm()) {
                if(contactDetailsPresenter.isEditMode()) {
                    contactDetailsPresenter.updateContact(name)
                } else {
                    contactDetailsPresenter.insertContact(name, phoneNumber)
                }
                finish()
            }
        }
    }

    /**
     * Defines the initial form values
     */
    private fun defineInitialValues() {
        val contact = contactDetailsPresenter.getContact()

        if(contactDetailsPresenter.isEditMode()) {
            dataBinding.edtCreateContactName.setText(contact?.name)
            contact?.photo?.let {
                definePhoto(it)
            }
        }

        val number = if(contact?.number != null) {
            contact.number
        } else {
            contactDetailsPresenter.generateRandomVoIPAppNumber()
        }

        dataBinding.txtCreateContactPhoneNumber.setText(number)
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