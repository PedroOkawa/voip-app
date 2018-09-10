package com.okawa.voip.utils.sync

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle

class SyncAdapter(context: Context, autoInitialize: Boolean) : AbstractThreadedSyncAdapter(context, autoInitialize) {

    override fun onPerformSync(account: Account?, extras: Bundle?, authority: String?, provider: ContentProviderClient?, syncResult: SyncResult?) {
        //TODO: Implement perform sync (Store data)
    }

}