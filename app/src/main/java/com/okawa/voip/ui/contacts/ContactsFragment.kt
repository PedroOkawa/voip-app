package com.okawa.voip.ui.contacts

import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.okawa.voip.R
import com.okawa.voip.databinding.FragmentContactsBinding
import com.okawa.voip.presenter.contacts.ContactsPresenter
import com.okawa.voip.ui.base.BaseFragment
import com.okawa.voip.utils.CursorUtils
import com.okawa.voip.utils.adapter.ContactAdapter
import javax.inject.Inject

class ContactsFragment : BaseFragment<FragmentContactsBinding>(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        const val ADD_CONTACT_QUERY_ID = 0x0000
        const val REMOVE_CONTACT_QUERY_ID = 0x0001
        const val LIST_CONTACTS_QUERY_ID = 0x0002

        fun newInstance(): ContactsFragment {
            return ContactsFragment()
        }
    }

    private val contactAdapter = ContactAdapter()

    @Inject
    lateinit var contactsPresenter: ContactsPresenter

    override fun layoutToInflate() = R.layout.fragment_contacts

    override fun doOnCreated() {
        initRecyclerView()
        initLoaderManager()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLoaderManager()
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(context!!,
                ContactsContract.Data.CONTENT_URI,
                CursorUtils.PROJECTION,
                CursorUtils.SELECTION_CLAUSE,
                CursorUtils.SELECTION_ARGUMENTS,
                CursorUtils.SORT_ORDER)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        contactAdapter.setData(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

    private fun initRecyclerView() {
        dataBinding.rclContactsContent.adapter = contactAdapter
        dataBinding.rclContactsContent.layoutManager = LinearLayoutManager(context)
    }

    private fun initLoaderManager() {
        loaderManager.initLoader(LIST_CONTACTS_QUERY_ID, null, this)
    }
}
