package com.example.booksapp.dagger

import android.content.Context
import com.example.injection.ForApplication
import com.example.injection.dagger.module.ModuleAppContext
import com.example.rx.dagger.ModuleCoreRx
import com.example.rx.scheduler.SchedulerConfiguration
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ModuleAppContext::class, ModuleCoreRx::class])
interface AppComponent {
    @ForApplication
    fun provideAppContext(): Context
    fun provideSchedulerConfiguration(): SchedulerConfiguration
}