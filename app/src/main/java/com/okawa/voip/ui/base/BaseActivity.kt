package com.okawa.voip.ui.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
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

        doOnCreated()
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

}