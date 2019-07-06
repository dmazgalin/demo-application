package com.example.bookrating.dagger

import com.example.booksapp.DemoApp
import com.example.bookrating.dagger.module.RatingFeatureModule
import com.example.bookrating.ui.BooksActivity

object Injector {
    fun inject(activity: BooksActivity) {
        DaggerRatingFeatureComponent.builder()
            .appComponent(DemoApp.appComponent(activity.applicationContext))
            .ratingFeatureModule(RatingFeatureModule())
            .build()
            .inject(activity)
    }
}