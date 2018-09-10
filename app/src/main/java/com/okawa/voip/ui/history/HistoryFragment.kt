package com.okawa.voip.ui.history

import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import com.okawa.voip.R
import com.okawa.voip.databinding.FragmentHistoryBinding
import com.okawa.voip.presenter.history.HistoryPresenter
import com.okawa.voip.ui.base.BaseFragment
import com.okawa.voip.utils.adapter.HistoryAdapter
import com.okawa.voip.model.History
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
    lateinit var historyPresenter: HistoryPresenter

    @Inject
    lateinit var historyMapper: HistoryMapper

    override fun layoutToInflate() = R.layout.fragment_history

    override fun doOnCreated() {
        initRecyclerView()
        initLoaderManager()
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
        historyAdapter.setCursor(cursor)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

    private fun initRecyclerView() {
        dataBinding.rclHistoryContent.adapter = historyAdapter
        dataBinding.rclHistoryContent.layoutManager = LinearLayoutManager(context)
    }

    private fun initLoaderManager() {
        loaderManager.initLoader(HISTORY_QUERY_ID, null, this)
    }
}