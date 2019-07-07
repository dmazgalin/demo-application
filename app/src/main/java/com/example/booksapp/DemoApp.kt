package com.example.booksapp

import android.app.Application
import android.content.Context
import com.example.booksapp.dagger.DaggerAppComponent
import com.example.injection.dagger.module.ModuleAppContext
import com.example.rx.dagger.ModuleCoreRx
import timber.log.Timber

class DemoApp : Application() {

    private val appComponent by lazy {
        DaggerAppComponent.builder()
            .moduleAppContext(ModuleAppContext(this))
            .moduleCoreRx(ModuleCoreRx())
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

    companion object {
        @JvmStatic
        fun appComponent(context: Context) = (context.applicationContext as DemoApp).appComponent
    }
}