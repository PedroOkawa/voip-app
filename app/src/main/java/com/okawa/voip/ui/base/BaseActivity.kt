package com.okawa.voip.ui.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.okawa.voip.BR
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    protected lateinit var dataBinding: T

    /**
     * Retrieved the layout to be inflated by the activity
     *
     * @return layout id to be inflated
     */
    @LayoutRes
    abstract fun layoutToInflate(): Int

    /**
     * Method called whenever the activity is created
     */
    abstract fun doOnCreated()

    override fun supportFragmentInjector() = fragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        defineDataBinding()

        savedInstanceState?.let {
            doOnRestoreInstance(it)
        }

        doOnCreated()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            doOnSaveInstance(it)
        }
    }

    override fun onDestroy() {
        dataBinding.unbind()
        super.onDestroy()
    }

    /**
     * Defines the data binding to be used by the activity
     */
    private fun defineDataBinding() {
        dataBinding = DataBindingUtil.setContentView(this, layoutToInflate())
    }

    protected open fun doOnRestoreInstance(savedInstanceState: Bundle) {

    }

    protected open fun doOnSaveInstance(outState: Bundle) {

    }

    protected fun showLoading(value: Boolean) {
        dataBinding.setVariable(BR.loading, value)
    }

    protected fun showEmpty(value: Boolean) {
        dataBinding.setVariable(BR.empty, value)
    }

}