package com.okawa.voip.repository.contacts

import android.database.Cursor
import com.okawa.voip.ContactMapper
import com.okawa.voip.model.Contact
import com.okawa.voip.utils.AppExecutors
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(private val appExecutors: AppExecutors, private val contactMapper: ContactMapper) : ContactsRepository {

    override fun retrieveContacts(cursor: Cursor?, listener: (List<Contact>) -> Unit) {
        cursor?.let {
            appExecutors.getDiskIO().execute {
                val result = ArrayList<Contact>()

                while (!it.isClosed && it.moveToNext()) {
                    result.add(contactMapper.convert(it))
                }

                listener.invoke(result)
            }
        }
    }

}