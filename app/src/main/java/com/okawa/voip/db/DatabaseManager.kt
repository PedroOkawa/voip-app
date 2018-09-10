package com.okawa.voip.db

import android.app.Application
import android.content.ContentProviderOperation
import android.content.ContentResolver
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
import com.okawa.voip.utils.utils.BitmapUtils
import java.io.FileNotFoundException


@Singleton
class DatabaseManager @Inject constructor(private val appExecutors: AppExecutors, application: Application, private val bitmapUtils: BitmapUtils, private val historyMapper: HistoryMapper) {

    companion object {
        const val VOIP_APP_PROFILE = "VoIP App Profile"
        const val VOIP_APP_VIEW_PROFILE = "View Profile"
    }

    private val contentResolver: ContentResolver by lazy {
        application.contentResolver
    }

    private val packageName: String by lazy {
        application.packageName
    }

    fun insertContact(name: String, number: String, photo: Uri?) {
        appExecutors.getDiskIO().execute {
            val operations = ArrayList<ContentProviderOperation>()
            var photoBlob: ByteArray? = null
            photo?.let {
                try {
                    photoBlob = bitmapUtils.convertCompact(contentResolver.openInputStream(it))
                } catch (exception: FileNotFoundException) {
                    exception.printStackTrace()
                }
            }

            operations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, name)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, packageName)
                    .build())

            operations.add(ContentProviderOperation.newInsert(ContactsContract.Settings.CONTENT_URI.buildUpon()
                    .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true").build())
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, name)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, packageName)
                    .withValue(ContactsContract.Settings.UNGROUPED_VISIBLE, 1)
                    .build())

            operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI.buildUpon()
                    .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true").build())
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                    .build())

            operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI.buildUpon()
                    .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true").build())
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
                    .build())

            operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI.buildUpon()
                    .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true").build())
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, photoBlob)
                    .build())

            operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI.buildUpon()
                    .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true").build())
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, DatabaseHelper.MIME_TYPE)
                    .withValue(ContactsContract.Data.DATA1, number)
                    .withValue(ContactsContract.Data.DATA2, VOIP_APP_PROFILE)
                    .withValue(ContactsContract.Data.DATA3, VOIP_APP_VIEW_PROFILE)
                    .build())

            contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)
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

            contentResolver.insert(History.CONTENT_URI, contentValues)
        }
    }

    fun updateContact(id: String, name: String, photo: Uri?) {
        appExecutors.getDiskIO().execute {
            val operations = ArrayList<ContentProviderOperation>()
            var photoBlob: ByteArray? = null
            photo?.let {
                try {
                    photoBlob = bitmapUtils.convertCompact(contentResolver.openInputStream(it))
                } catch (exception: FileNotFoundException) {
                    exception.printStackTrace()
                }
            }


            operations.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection("${ContactsContract.Data.CONTACT_ID} =? AND ${ContactsContract.Data.MIMETYPE} =?", arrayOf(id, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE))
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                    .build())

            operations.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection("${ContactsContract.Data.CONTACT_ID} =? AND ${ContactsContract.Data.MIMETYPE} =?", arrayOf(id, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE))
                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, photoBlob)
                    .build())

            /*
            operations.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.Data.RAW_CONTACT_ID, arrayOf(id))
                    .withExpectedCount(1)
                    .withValue(ContactsContract.Data.DISPLAY_NAME, name)
                    .build())
                    */

            /*
            operations.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.Data.CONTACT_ID, arrayOf(id))
                    .withValue(ContactsContract.Contacts.PHOTO, photoBlob)
                    .build())
                    */

            contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)
        }
    }

}