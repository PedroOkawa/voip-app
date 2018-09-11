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
import android.widget.Toast
import com.okawa.voip.R
import com.okawa.voip.databinding.FragmentContactsBinding
import com.okawa.voip.presenter.contacts.ContactsPresenter
import com.okawa.voip.ui.base.BaseFragment
import com.okawa.voip.model.Contact
import com.okawa.voip.utils.adapter.ContactActionsAdapter
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

    @Inject
    lateinit var callManager: CallManager

    @Inject
    lateinit var contactMapper: ContactMapper

    @Inject
    lateinit var contactsPresenter: ContactsPresenter

    private var currentCursorQueryId = ALL_CONTACTS_QUERY_ID

    private var filterItemPosition: Int? = null

    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(contactMapper)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_action_contacts, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_add_contact) {
            context?.let {
                startActivity(callManager.createContact(it))
            }
            return true
        }
        return false
    }

    override fun layoutToInflate() = R.layout.fragment_contacts

    override fun doOnCreated() {
        setHasOptionsMenu(true)
        initTabLayout()
        initRecyclerView()
        retrieveContent()
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
        handleResult(loader.id, cursor)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        handleResult(loader.id, null)
    }

    /**
     * Initializes the tab layout setting a listener to check which query should be requested
     */
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

        restoreFilterItemSelection()
    }

    /**
     * Initializes the recycler view with the contacts adapter
     */
    private fun initRecyclerView() {
        contactAdapter.defineTouchListener { actionType, contact ->
            handleAction(actionType, contact)
        }
        dataBinding.rclContactsContent.adapter = contactAdapter
        dataBinding.rclContactsContent.layoutManager = LinearLayoutManager(context)
    }

    /**
     * Retrieves the content to be loaded
     */
    private fun retrieveContent() {
        if(loaderManager.getLoader<Cursor>(ALL_CONTACTS_QUERY_ID) != null) {
            loadData(ALL_CONTACTS_QUERY_ID)
        } else {
            retrieveInitialContacts()
        }
    }

    /**
     * Retrieves the initial content to be loaded
     */
    private fun retrieveInitialContacts() {
        showLoading(true)
        loaderManager.initLoader(ALL_CONTACTS_QUERY_ID, null, this)
    }

    /**
     * Loads the data based on the given query ID
     *
     * @param queryId of the loader
     */
    private fun loadData(queryId: Int) {
        showLoading(true)
        currentCursorQueryId = queryId
        loaderManager.destroyLoader(ALL_CONTACTS_QUERY_ID)
        loaderManager.destroyLoader(VOIP_APP_CONTACTS_QUERY_ID)
        loaderManager.restartLoader(queryId, null, this)
    }

    /**
     * Handles the filter position to retrieve the correct data
     *
     * @param position tab position
     */
    private fun handleFilter(position: Int) {
        if(position == ALL_CONTACTS_TAB_POSITION) {
            loadData(ALL_CONTACTS_QUERY_ID)
        } else {
            loadData(VOIP_APP_CONTACTS_QUERY_ID)
        }
    }

    /**
     * Handles the action clicked on the adapter item
     *
     * @param actionType Type of the action clicked. It could be ACTION_TYPE_CALL or ACTION_TYPE_EDIT
     * @param contact clicked
     */
    private fun handleAction(actionType: Int, contact: Contact?) {
        contact?.let {
            if(actionType == ContactActionsAdapter.ACTION_TYPE_CALL) {
                Toast.makeText(context, context?.getString(R.string.contacts_called_message, contact.name), Toast.LENGTH_SHORT).show()
                contactsPresenter.insertHistory(contact)
            } else {
                context?.let {
                    startActivity(callManager.details(it, contact))
                }
            }
        }
    }

    /**
     * Restores the filter item selection
     */
    private fun restoreFilterItemSelection() {
        filterItemPosition?.let {
            if(it == VOIP_APP_CONTACTS_TAB_POSITION) {
                dataBinding.tblContactsFilter.getTabAt(it)?.select()
            }
        }
    }

    /**
     * handles the result of the cursor
     *
     * @param queryId cursor loader queryID
     * @param cursor to be loaded
     */
    private fun handleResult(queryId: Int, cursor: Cursor?) {
        if(queryId == currentCursorQueryId) {
            showLoading(false)
            showEmpty(cursor == null || cursor.count == 0)

            contactAdapter.setCursor(cursor)
        }
    }
}
