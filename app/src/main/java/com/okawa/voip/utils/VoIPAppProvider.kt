package com.okawa.voip.utils

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.okawa.voip.db.DatabaseHelper
import com.okawa.voip.model.History
import dagger.android.AndroidInjection
import javax.inject.Inject

class VoIPAppProvider: ContentProvider() {

    @Inject
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(): Boolean {
        AndroidInjection.inject(this)
        return true
    }

    override fun insert(uri: Uri?, contentValues: ContentValues?): Uri? {
        databaseHelper.writableDatabase.insertOrThrow(History.TABLE_NAME, null, contentValues)
        return uri
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        val sqLiteQueBuilder = SQLiteQueryBuilder()

        sqLiteQueBuilder.tables = History.TABLE_NAME
        return sqLiteQueBuilder.query(databaseHelper.readableDatabase,
                History.PROJECTION,
                History.SELECTION_CLAUSE,
                History.SELECTION_ARGUMENTS,
                null,
                null,
                History.SORT_ORDER)
    }

    override fun getType(uri: Uri?) = null

    override fun update(uri: Uri?, contentValues: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return databaseHelper.readableDatabase.update(History.TABLE_NAME, contentValues, selection, selectionArgs)
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        return databaseHelper.readableDatabase.delete(History.TABLE_NAME, selection, selectionArgs)
    }
}