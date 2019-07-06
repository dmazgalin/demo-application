package com.example.bookrating.dagger

import com.example.booksapp.dagger.AppComponent
import com.example.bookrating.dagger.module.RatingFeatureModule
import com.example.bookrating.ui.BooksActivity
import com.example.injection.FeatureScope
import dagger.Component


@FeatureScope
@Component(dependencies = [AppComponent::class], modules = [RatingFeatureModule::class])
interface RatingFeatureComponent {
    fun inject(activity: BooksActivity)
}