package com.example.notekotlin.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.notekotlin.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity < T, S : BaseViewState<T>> : AppCompatActivity() {
    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int
    override fun onCreate (savedInstanceState: Bundle ?) {
        super .onCreate(savedInstanceState)
        setContentView(layoutRes)
        viewModel.getViewState().observe( this ,Observer<S> { t ->
            t?.apply {
                data ?.let { renderData(it) }
                error?.let { renderError(it) }
            }
        })
    }
    protected fun renderError (error: Throwable ) {
        error.message?.let { showError(it) }
    }
    abstract fun renderData ( data : T )
    protected fun showError (error: String ) {
        Snackbar.make(mainRecycler, error, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.ok_bth_title) { dismiss() }
            show()
        }
    }
}