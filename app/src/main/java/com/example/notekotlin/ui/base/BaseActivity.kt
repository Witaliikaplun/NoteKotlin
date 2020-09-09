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
    override fun onCreate (savedInstanceState: Bundle?) {
        super .onCreate(savedInstanceState)
        setContentView(layoutRes)
        viewModel.getViewState().observe( this , object : Observer<S> {
            override fun onChanged (t: S?) {
                if (t == null ) return
                if (t.data != null ) renderData(t.data!!)
                if (t.error != null ) renderError(t.error)
            }
        })
    }
    protected fun renderError (error: Throwable ) {
        if (error.message != null ) showError(error.message!!)
    }
    abstract fun renderData ( data : T )
    protected fun showError (error: String ) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}