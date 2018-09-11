package com.okawa.voip.ui.history

import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.okawa.voip.R
import com.okawa.voip.databinding.FragmentHistoryBinding
import com.okawa.voip.presenter.history.HistoryPresenter
import com.okawa.voip.ui.base.BaseFragment
import com.okawa.voip.utils.adapter.HistoryAdapter
import com.okawa.voip.model.History
import com.okawa.voip.utils.adapter.ContactActionsAdapter
import com.okawa.voip.utils.manager.CallManager
import com.okawa.voip.utils.mapper.HistoryMapper
import javax.inject.Inject

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        private const val HISTORY_QUERY_ID = 0x0003

        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    private val historyAdapter: HistoryAdapter by lazy {
        HistoryAdapter(historyMapper)
    }

    @Inject
    lateinit var callManager: CallManager

    @Inject
    lateinit var historyPresenter: HistoryPresenter

    @Inject
    lateinit var historyMapper: HistoryMapper

    override fun layoutToInflate() = R.layout.fragment_history

    override fun doOnCreated() {
        initRecyclerView()
        retrieveHistory()
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(context!!,
                History.CONTENT_URI,
                History.PROJECTION,
                History.SELECTION_CLAUSE,
                History.SELECTION_ARGUMENTS,
                History.SORT_ORDER)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        handleResult(cursor)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        handleResult(null)
    }

    /**
     * Initializes the recycler view with the history adapter
     */
    private fun initRecyclerView() {
        historyAdapter.defineTouchListener { actionType, history ->
            handleAction(actionType, history)
        }
        dataBinding.rclHistoryContent.adapter = historyAdapter
        dataBinding.rclHistoryContent.layoutManager = LinearLayoutManager(context)
    }

    /**
     * Retrieves the content to be loaded
     */
    private fun retrieveHistory() {
        showLoading(true)
        loaderManager.initLoader(HISTORY_QUERY_ID, null, this)
    }

    /**
     * Handles the action clicked on the adapter item
     *
     * @param actionType Type of the action clicked. It could be ACTION_TYPE_CALL or ACTION_TYPE_EDIT
     * @param history clicked
     */
    private fun handleAction(actionType: Int, history: History?) {
        history?.let {
            if(actionType == ContactActionsAdapter.ACTION_TYPE_CALL) {
                Toast.makeText(context, context?.getString(R.string.contacts_called_message, history.name), Toast.LENGTH_SHORT).show()
                historyPresenter.insertHistory(history)
            } else {
                context?.let {
                    startActivity(callManager.details(it, history))
                }
            }
        }
    }

    /**
     * handles the result of the cursor
     *
     * @param cursor to be loaded
     */
    private fun handleResult(cursor: Cursor?) {
        showLoading(false)
        showEmpty(cursor == null || cursor.count == 0)

        historyAdapter.setCursor(cursor)
    }
}