package com.okawa.voip.db

import android.app.Application
import android.content.ContentProviderOperation
import android.content.ContentValues
import android.net.Uri
import android.provider.ContactsContract
import com.okawa.voip.model.Contact
import com.okawa.voip.model.History
import com.okawa.voip.utils.executors.AppExecutors
import com.okawa.voip.utils.extensions.toInt
import com.okawa.voip.utils.mapper.HistoryMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseManager @Inject constructor(private val appExecutors: AppExecutors, private val application: Application, private val historyMapper: HistoryMapper) {

    companion object {
        const val VOIP_APP_SUFFIX = "VoIPApp"
        const val VOIP_APP_PROFILE = "VoIP App Profile"
        const val VOIP_APP_VIEW_PROFILE = "View Profile"
    }

    fun insertContact(contact: Contact) {
        appExecutors.getDiskIO().execute {
            val operations = ArrayList<ContentProviderOperation>()

            operations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, contact.name)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, application.packageName)
                    .build())

            operations.add(ContentProviderOperation.newInsert(ContactsContract.Settings.CONTENT_URI.buildUpon()
                    .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true").build())
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, contact.name)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, application.packageName)
                    .withValue(ContactsContract.Settings.UNGROUPED_VISIBLE, 1)
                    .build())

            operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI.buildUpon()
                    .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true").build())
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.name)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contact.name)
                    .build())

            operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI.buildUpon()
                    .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true").build())
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.number)
                    .build())

            operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI.buildUpon()
                    .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true").build())
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, DatabaseHelper.MIME_TYPE)
                    .withValue(ContactsContract.Data.DATA1, "${contact.number}$VOIP_APP_SUFFIX")
                    .withValue(ContactsContract.Data.DATA2, VOIP_APP_PROFILE)
                    .withValue(ContactsContract.Data.DATA3, VOIP_APP_VIEW_PROFILE)
                    .build())

            application.contentResolver?.applyBatch(ContactsContract.AUTHORITY, operations)
        }
    }

    fun insertHistory(contact: Contact) {
        appExecutors.getDiskIO().execute {
            val history = historyMapper.convert(contact)

            val contentValues = ContentValues()

            contentValues.put(History.COLUMN_NAME, history.name)
            contentValues.put(History.COLUMN_NUMBER, history.number)
            contentValues.put(History.COLUMN_PHOTO, history.photo.toString())
            contentValues.put(History.COLUMN_IS_VOIP_APP, history.isVoIPApp.toInt())
            contentValues.put(History.COLUMN_DATE, history.date.time)

            application.contentResolver.insert(History.CONTENT_URI, contentValues)
        }
    }

}