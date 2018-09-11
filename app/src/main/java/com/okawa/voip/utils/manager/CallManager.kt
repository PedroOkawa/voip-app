package com.okawa.voip.utils.manager

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import com.okawa.voip.ui.contacts.ContactsFragment
import com.okawa.voip.ui.details.ContactDetailsActivity
import com.okawa.voip.ui.history.HistoryFragment
import com.okawa.voip.ui.main.MainActivity
import com.okawa.voip.ui.onboarding.OnBoardingActivity
import com.okawa.voip.ui.settings.SettingsFragment
import javax.inject.Inject
import javax.inject.Singleton
import com.okawa.voip.R
import com.okawa.voip.model.Contact
import com.okawa.voip.model.History
import com.okawa.voip.utils.mapper.ContactMapper
import android.support.annotation.StringRes


@Singleton
class CallManager @Inject constructor(private val contactMapper: ContactMapper) {

    companion object {
        private const val IMAGES_INTENT_TYPE = "image/*"
    }

    /**
     * Retrieves Intent based on OnBoarding activity
     *
     * @param context used to create the intent
     *
     * @return OnBoardingActivity intent
     */
    fun onBoarding(context: Context) : Intent {
        val intent = Intent(context, OnBoardingActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        return intent
    }

    /**
     * Retrieves Intent based on Main activity
     *
     * @param context used to create the intent
     *
     * @return MainActivity intent
     */
    fun main(context: Context) : Intent {
        val intent = Intent(context, MainActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        return intent
    }

    /**
     * Retrieves Intent based on Create Contact activity
     *
     * @param context used to create the intent
     *
     * @return ContactDetailsActivity intent
     */
    fun createContact(context: Context) = Intent(context, ContactDetailsActivity::class.java)

    /**
     * Retrieves Intent based on Contact Details activity
     *
     * @param context used to create the intent
     * @param contact that will the details
     *
     * @return ContactDetailsActivity intent
     */
    fun details(context: Context, contact: Contact): Intent {
        val intent = Intent(context, ContactDetailsActivity::class.java)
        intent.putExtra(ContactDetailsActivity.BUNDLE_CONTACTS, contact)
        return intent
    }

    /**
     * Retrieves Intent based on Contact Details activity
     *
     * @param context used to create the intent
     * @param history that will the details
     *
     * @return ContactDetailsActivity intent
     */
    fun details(context: Context, history: History): Intent {
        val intent = Intent(context, ContactDetailsActivity::class.java)
        val contact = contactMapper.convert(history)
        intent.putExtra(ContactDetailsActivity.BUNDLE_CONTACTS, contact)
        return intent
    }

    /**
     * Retrieves Intent to retrieve images
     *
     * @param context used to retrieve intent title
     *
     * @return Image Picker intent
     */
    fun images(context: Context): Intent {
        val intent = Intent()
        intent.type = IMAGES_INTENT_TYPE
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        return Intent.createChooser(intent, context.getString(R.string.common_picture_intent_title))
    }

    /**
     * Replaces the fragment on the specified container ID by a Contacts Fragment
     *
     * @param fragmentManager used to begin and execute the fragment transaction
     * @param containerId view id that will be used to replace the fragment
     */
    fun contacts(fragmentManager: FragmentManager, @IdRes containerId: Int) {
        fragmentManager.beginTransaction().replace(containerId, ContactsFragment.newInstance()).commit()
    }

    /**
     * Replaces the fragment on the specified container ID by a History Fragment
     *
     * @param fragmentManager used to begin and execute the fragment transaction
     * @param containerId view id that will be used to replace the fragment
     */
    fun history(fragmentManager: FragmentManager, @IdRes containerId: Int) {
        fragmentManager.beginTransaction().replace(containerId, HistoryFragment.newInstance()).commit()
    }

    /**
     * Replaces the fragment on the specified container ID by a Settings Fragment
     *
     * @param fragmentManager used to begin and execute the fragment transaction
     * @param containerId view id that will be used to replace the fragment
     */
    fun settings(fragmentManager: FragmentManager, @IdRes containerId: Int) {
        fragmentManager.beginTransaction().replace(containerId, SettingsFragment.newInstance()).commit()
    }

    /**
     * Shows the delete contact confirmation dialog
     *
     * @param context used to create dialog
     * @param positiveListener listener that will be triggered when positive button is selected
     */
    fun deleteContactConfirmation(context: Context, positiveListener: () -> Unit) {
        showDialog(context, R.string.delete_contact_confirmation_title, R.string.delete_confirmation_yes, R.string.delete_confirmation_no, positiveListener) { }
    }

    /**
     * Shows the delete history confirmation dialog
     *
     * @param context used to create dialog
     * @param positiveListener listener that will be triggered when positive button is selected
     */
    fun deleteHistoryConfirmation(context: Context, positiveListener: () -> Unit) {
        showDialog(context, R.string.delete_history_confirmation_title, R.string.delete_confirmation_yes, R.string.delete_confirmation_no, positiveListener) { }
    }

    /**
     * Shows the delete all data confirmation dialog
     *
     * @param context used to create dialog
     * @param positiveListener listener that will be triggered when positive button is selected
     */
    fun deleteAllDataConfirmation(context: Context, positiveListener: () -> Unit) {
        showDialog(context, R.string.delete_all_data_confirmation_title, R.string.delete_confirmation_yes, R.string.delete_confirmation_no, positiveListener) { }
    }

    /**
     * Shows a dialog based on the given parameters
     *
     * @param context used to create dialog
     * @param title text used to be shown at the title
     * @param positive text used to be shown at the positive button
     * @param negative text used to be shown at the negative button
     * @param positiveListener listener that will be triggered when positive button is selected
     * @param negativeListener listener that will be triggered when negative button is selected
     */
    private fun showDialog(context: Context, @StringRes title: Int, @StringRes positive: Int, @StringRes negative: Int, positiveListener: () -> Unit, negativeListener: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(title)
                .setPositiveButton(positive) { dialog, _ ->
                    positiveListener.invoke()
                    dialog.dismiss()
                }
                .setNegativeButton(negative) { dialog, _ ->
                    negativeListener.invoke()
                    dialog.dismiss()
                }
        builder.create().show()
    }

}

