package com.okawa.voip.ui.settings

import com.okawa.voip.R
import com.okawa.voip.databinding.FragmentSettingsBinding
import com.okawa.voip.presenter.settings.SettingsPresenter
import com.okawa.voip.ui.base.BaseFragment
import com.okawa.voip.utils.manager.CallManager
import javax.inject.Inject

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    @Inject
    lateinit var callManager: CallManager

    @Inject
    lateinit var settingsPresenter: SettingsPresenter

    override fun layoutToInflate() = R.layout.fragment_settings

    override fun doOnCreated() {
        initClearHistoryButton()
        initClearAllDataButton()
    }

    private fun initClearHistoryButton() {
        dataBinding.btnSettingsClearHistory.setOnClickListener {
            context?.let {
                callManager.deleteHistoryConfirmation(it) {
                    settingsPresenter.clearHistory()
                }
            }
        }
    }

    private fun initClearAllDataButton() {
        dataBinding.btnSettingsClearData.setOnClickListener {
            context?.let {
                callManager.deleteAllDataConfirmation(it) {
                    settingsPresenter.clearAllData()
                }
            }
        }
    }

}