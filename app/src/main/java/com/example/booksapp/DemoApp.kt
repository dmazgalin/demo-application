package com.example.booksapp

import android.app.Application
import android.content.Context
import com.example.booksapp.dagger.DaggerAppComponent
import com.example.injection.dagger.module.ModuleAppContext
import com.example.rx.dagger.ModuleCoreRx

class DemoApp : Application() {

    private val appComponent by lazy {
        DaggerAppComponent.builder()
            .moduleAppContext(ModuleAppContext(this))
            .moduleCoreRx(ModuleCoreRx())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        @JvmStatic
        fun appComponent(context: Context) = (context.applicationContext as DemoApp).appComponent
    }
}