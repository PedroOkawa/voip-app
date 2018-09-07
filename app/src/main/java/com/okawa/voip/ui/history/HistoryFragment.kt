package com.okawa.voip.ui.history

import android.database.Cursor
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import com.okawa.voip.R
import com.okawa.voip.databinding.FragmentHistoryBinding
import com.okawa.voip.presenter.history.HistoryPresenter
import com.okawa.voip.repository.history.ListHistorySuccess
import com.okawa.voip.repository.status.Result
import com.okawa.voip.ui.base.BaseFragment
import com.okawa.voip.utils.adapter.HistoryAdapter
import com.okawa.voip.utils.utils.CursorUtils
import javax.inject.Inject

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        const val HISTORY_QUERY_ID = 0x0003

        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    private val historyAdapter = HistoryAdapter()

    @Inject
    lateinit var historyPresenter: HistoryPresenter

    override fun layoutToInflate() = R.layout.fragment_history

    override fun doOnCreated() {
        initRecyclerView()
        initResultObservable()
        initLoaderManager()
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(context!!,
                CallLog.Calls.CONTENT_URI,
                CursorUtils.History.PROJECTION,
                CursorUtils.History.SELECTION_CLAUSE,
                CursorUtils.History.SELECTION_ARGUMENTS,
                CursorUtils.History.SORT_ORDER)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        historyPresenter.retrieveHistory(cursor)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

    private fun initResultObservable() {
        enqueueObservable(historyPresenter
                .getResultObservable()
                .subscribe { result ->
                    handleResult(result)
                })
    }

    private fun initRecyclerView() {
        dataBinding.rclHistoryContent.adapter = historyAdapter
        dataBinding.rclHistoryContent.layoutManager = LinearLayoutManager(context)
    }

    private fun initLoaderManager() {
        loaderManager.initLoader(HISTORY_QUERY_ID, null, this)
    }

    private fun handleResult(result: Result) {
        if(result is ListHistorySuccess) {
            historyAdapter.setData(result.getData())
        }
    }
}