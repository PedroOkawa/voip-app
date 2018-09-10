package com.okawa.voip.ui.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    private val compositeDisposable = CompositeDisposable()

    protected lateinit var dataBinding: T

    /**
     * Retrieves the layout to be inflated by the fragment
     *
     * @return layout id to be inflated
     */
    @LayoutRes
    abstract fun layoutToInflate(): Int

    /**
     * Method called whenever the fragment is created
     */
    abstract fun doOnCreated()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            doOnRestoreInstance(it)
        }
        doOnCreated()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            defineDataBinding(inflater, container)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        doOnSaveInstance(outState)
    }

    override fun onDestroyView() {
        dispose()
        super.onDestroyView()
    }

    /**
     * Defines the data binding to be used by the fragment
     *
     * @param inflater layout inflater
     * @param container view container that will hold the inflated layout
     *
     * @return inflated view
     */
    private fun defineDataBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        dataBinding = DataBindingUtil.inflate(inflater, layoutToInflate(), container, false)
        return dataBinding.root
    }

    protected fun enqueueObservable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected open fun dispose() {
        compositeDisposable.clear()
        dataBinding.unbind()
    }

    protected open fun doOnRestoreInstance(savedInstanceState: Bundle) {

    }

    protected open fun doOnSaveInstance(outState: Bundle) {

    }

}