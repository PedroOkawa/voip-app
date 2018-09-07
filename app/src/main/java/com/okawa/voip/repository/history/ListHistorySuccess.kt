package com.okawa.voip.repository.history

import com.okawa.voip.model.History
import com.okawa.voip.repository.status.OnSuccess

class ListHistorySuccess(history: List<History>) : OnSuccess<List<History>>(history)