package com.example.notekotlin.ui.splash

import android.os.Handler
import androidx.lifecycle.ViewModelProviders
import com.example.notekotlin.ui.base.BaseActivity
import com.example.notekotlin.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

private const val START_DELAY = 1000L

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {
    override val viewModel: SplashViewModel by viewModel()
    override val layoutRes = null
    override fun onResume() {
        super.onResume()
        Handler().postDelayed({ viewModel.requestUser() }, START_DELAY)
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }
}