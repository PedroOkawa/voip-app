package com.okawa.voip.ui.contacts

import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.util.Log
import com.okawa.voip.R
import com.okawa.voip.databinding.FragmentContactsBinding
import com.okawa.voip.ui.base.BaseFragment
import com.okawa.voip.utils.CursorUtils

class ContactsFragment : BaseFragment<FragmentContactsBinding>(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        const val ADD_CONTACT_QUERY_ID = 0x0000
        const val REMOVE_CONTACT_QUERY_ID = 0x0001
        const val LIST_CONTACTS_QUERY_ID = 0x0002

        fun newInstance(): ContactsFragment {
            return ContactsFragment()
        }
    }

    override fun layoutToInflate() = R.layout.fragment_contacts

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLoaderManager()
    }

    override fun doOnCreated() {

    }

    override fun onCreateLoader(queryId: Int, args: Bundle?): Loader<Cursor> {
        /*
        return when(queryId) {
            ADD_CONTACT_QUERY_ID -> {

            }
            REMOVE_CONTACT_QUERY_ID -> {

            }
            LIST_CONTACTS_QUERY_ID -> {
            */
                //activity?.let {
                    return CursorLoader(activity!!,
                            ContactsContract.Data.CONTENT_URI,
                            CursorUtils.PROJECTION,
                            CursorUtils.SELECTION,
                            CursorUtils.SELECTION_ARGUMENTS,
                            CursorUtils.SORT_ORDER)
                //}
        /*
            }
            else -> {  }
        }

        return null
        */
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        Log.w("TEST", "DATA: $data")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

    private fun initLoaderManager() {
        loaderManager.initLoader(LIST_CONTACTS_QUERY_ID, null, this)
    }
}
