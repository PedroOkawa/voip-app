package com.okawa.voip.utils.sync

import android.app.Service
import android.content.Intent

class SyncService : Service() {

    private val syncAdapter: SyncAdapter by lazy {
        SyncAdapter(applicationContext, true)
    }

    override fun onBind(p0: Intent?) = syncAdapter.syncAdapterBinder
}