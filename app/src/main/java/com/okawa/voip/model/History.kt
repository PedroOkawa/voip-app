package com.okawa.voip.model

import android.net.Uri
import com.okawa.voip.db.DatabaseHelper
import java.util.*

data class History(val name: String, val number: String, val photo: Uri?, val isVoIPApp: Boolean, val date: Date) {

    companion object {

        private const val CONTENT = "${DatabaseHelper.SCHEME}${DatabaseHelper.AUTHORITY}/history"

        val CONTENT_URI: Uri = Uri.parse(CONTENT)

        const val TABLE_NAME = "history"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_NUMBER = "number"
        const val COLUMN_PHOTO = "photo"
        const val COLUMN_IS_VOIP_APP = "is_voip_app"
        const val COLUMN_DATE = "date"

        val PROJECTION = arrayOf(
                COLUMN_NAME,
                COLUMN_PHOTO,
                COLUMN_NUMBER,
                COLUMN_IS_VOIP_APP,
                COLUMN_DATE
        )

        val SELECTION_ARGUMENTS = null

        val SELECTION_CLAUSE = null

        const val SORT_ORDER = "$COLUMN_DATE DESC "
    }

    var id: Long = 0

}