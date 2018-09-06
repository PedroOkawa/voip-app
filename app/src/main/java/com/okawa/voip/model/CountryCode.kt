package com.okawa.voip.model

import android.os.Parcel
import android.os.Parcelable

data class CountryCode(val code: Int, val region: String, val name: String) : Parcelable {

    constructor(parcel: Parcel) : this( parcel.readInt(), parcel.readString(), parcel.readString())

    override fun writeToParcel(out: Parcel?, flags: Int) {
        out?.writeInt(code)
        out?.writeString(region)
        out?.writeString(name)
    }

    override fun describeContents() = 0

    override fun toString(): String {
        return name
    }

    companion object CREATOR : Parcelable.Creator<CountryCode> {
        override fun createFromParcel(parcel: Parcel): CountryCode {
            return CountryCode(parcel)
        }

        override fun newArray(size: Int): Array<CountryCode?> {
            return arrayOfNulls(size)
        }
    }

}