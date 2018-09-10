package com.okawa.voip.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract
import com.okawa.voip.db.DatabaseHelper

data class Contact(val id: String, val name: String, val number: String, val photo: Uri?, val isVoIPApp: Boolean): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Uri::class.java.classLoader),
            parcel.readByte() != 0.toByte())

    companion object {

        val CONTENT_URI: Uri = ContactsContract.Data.CONTENT_URI

        val PROJECTION = arrayOf(
                ContactsContract.Data._ID,
                ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Data.PHOTO_URI,
                ContactsContract.CommonDataKinds.Photo.PHOTO,
                ContactsContract.Data.MIMETYPE
        )

        val SELECTION_ALL_CLAUSE = "${ContactsContract.CommonDataKinds.Phone.MIMETYPE} IN (?, ?)"

        val SELECTION_ALL_ARGUMENTS = arrayOf(DatabaseHelper.MIME_TYPE, "vnd.android.cursor.item/phone_v2")

        val SELECTION_VOIP_APP_CLAUSE = "${ContactsContract.CommonDataKinds.Phone.MIMETYPE} = ?"

        val SELECTION_VOIP_APP_ARGUMENTS = arrayOf(DatabaseHelper.MIME_TYPE)

        const val SORT_ORDER = ContactsContract.Data.DISPLAY_NAME

        @JvmField val CREATOR = object : Parcelable.Creator<Contact> {
            override fun createFromParcel(parcel: Parcel): Contact {
                return Contact(parcel)
            }

            override fun newArray(size: Int): Array<Contact?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(out: Parcel?, p1: Int) {
        out?.writeString(id)
        out?.writeString(name)
        out?.writeString(number)
        out?.writeParcelable(photo, p1)
        out?.writeByte(if (isVoIPApp) 1 else 0)
    }

    override fun describeContents() = 0

}