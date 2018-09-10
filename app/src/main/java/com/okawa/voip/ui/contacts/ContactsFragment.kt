package com.okawa.voip.ui.contacts

import android.database.Cursor
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.okawa.voip.R
import com.okawa.voip.databinding.FragmentContactsBinding
import com.okawa.voip.presenter.contacts.ContactsPresenter
import com.okawa.voip.ui.base.BaseFragment
import com.okawa.voip.model.Contact
import com.okawa.voip.utils.adapter.ContactAdapter
import com.okawa.voip.utils.manager.CallManager
import com.okawa.voip.utils.mapper.ContactMapper
import javax.inject.Inject

class ContactsFragment : BaseFragment<FragmentContactsBinding>(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        private const val BUNDLE_FILTER_ITEM_POSITION = "filter_item_position"

        private const val ALL_CONTACTS_TAB_POSITION = 0
        private const val VOIP_APP_CONTACTS_TAB_POSITION = 1

        private const val ALL_CONTACTS_QUERY_ID = 0x0001
        private const val VOIP_APP_CONTACTS_QUERY_ID = 0x0002

        fun newInstance(): ContactsFragment {
            return ContactsFragment()
        }
    }

    private var filterItemPosition: Int? = null

    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(contactMapper)
    }

    @Inject
    lateinit var callManager: CallManager

    @Inject
    lateinit var contactMapper: ContactMapper

    @Inject
    lateinit var contactsPresenter: ContactsPresenter

    override fun layoutToInflate() = R.layout.fragment_contacts

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_action_contacts, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_add_contact) {
            //startActivity()
            return true
        }
        return false
    }

    override fun doOnCreated() {
        setHasOptionsMenu(true)
        initTabLayout()
        initRecyclerView()
        retrieveInitialContent()
    }

    override fun doOnRestoreInstance(savedInstanceState: Bundle) {
        filterItemPosition = savedInstanceState.getInt(BUNDLE_FILTER_ITEM_POSITION)
    }

    override fun doOnSaveInstance(outState: Bundle) {
        outState.putInt(BUNDLE_FILTER_ITEM_POSITION, dataBinding.tblContactsFilter.selectedTabPosition)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(context!!,
                Contact.CONTENT_URI,
                Contact.PROJECTION,
                if(id == ALL_CONTACTS_QUERY_ID) Contact.SELECTION_ALL_CLAUSE else Contact.SELECTION_VOIP_APP_CLAUSE,
                if(id == ALL_CONTACTS_QUERY_ID) Contact.SELECTION_ALL_ARGUMENTS else Contact.SELECTION_VOIP_APP_ARGUMENTS,
                Contact.SORT_ORDER)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        contactAdapter.setCursor(cursor)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) { }

    private fun initTabLayout() {
        dataBinding.tblContactsFilter.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) { }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    handleFilter(it.position)
                }
            }
        })

        filterItemPosition?.let {
            if(it == VOIP_APP_CONTACTS_TAB_POSITION) {
                dataBinding.tblContactsFilter.getTabAt(it)?.select()
            }
        }
    }

    private fun initRecyclerView() {
        contactAdapter.defineTouchListener { contact ->
            contactsPresenter.insertHistory(contact)
        }
        dataBinding.rclContactsContent.adapter = contactAdapter
        dataBinding.rclContactsContent.layoutManager = LinearLayoutManager(context)
    }

    private fun retrieveInitialContent() {
        loaderManager.initLoader(ALL_CONTACTS_QUERY_ID, null, this)
    }

    private fun retrieveAllContacts() {
        loaderManager.restartLoader(ALL_CONTACTS_QUERY_ID, null, this)
    }

    private fun retrieveVoIPAppContacts() {
        loaderManager.restartLoader(VOIP_APP_CONTACTS_QUERY_ID, null, this)
    }

    private fun handleFilter(position: Int) {
        if(position == ALL_CONTACTS_TAB_POSITION) {
            retrieveAllContacts()
        } else {
            retrieveVoIPAppContacts()
        }
    }
}
