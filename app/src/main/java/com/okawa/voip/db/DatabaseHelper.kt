package com.okawa.voip.db

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.okawa.voip.model.Contact
import com.okawa.voip.model.History
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelper @Inject constructor(application: Application) : SQLiteOpenHelper(application, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        const val MIME_TYPE = "vnd.android.cursor.item/com.okawa.voip"

        const val AUTHORITY = "com.okawa.voip"
        const val SCHEME = "content://"

        private const val DATABASE_NAME = "voipapp.db"
        private const val DATABASE_VERSION = 1

        private val TYPE_BLOB = " BLOB"
        private val TYPE_INTEGER = " INTEGER"
        private val TYPE_TEXT = " TEXT"

        private val SQL_CREATE_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS ${History.TABLE_NAME} (" +
                "${History.COLUMN_ID}$TYPE_INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${History.COLUMN_NAME}$TYPE_TEXT," +
                "${History.COLUMN_CONTACT_ID}$TYPE_TEXT," +
                "${History.COLUMN_NUMBER}$TYPE_TEXT," +
                "${History.COLUMN_PHOTO}$TYPE_BLOB," +
                "${History.COLUMN_IS_VOIP_APP}$TYPE_INTEGER," +
                "${History.COLUMN_DATE}$TYPE_INTEGER)"

        private const val SQL_DROP_HISTORY_TABLE = "DROP TABLE IF EXISTS " + History.TABLE_NAME
    }

    override fun onCreate(database: SQLiteDatabase?) {
        createTables(database)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        dropTables(database)
        createTables(database)
    }

    private fun createTables(database: SQLiteDatabase?) {
        database?.let {
            it.execSQL(SQL_CREATE_HISTORY_TABLE)
        }
    }

    private fun dropTables(database: SQLiteDatabase?) {
        database?.let {
            it.execSQL(SQL_DROP_HISTORY_TABLE)
        }
    }

}