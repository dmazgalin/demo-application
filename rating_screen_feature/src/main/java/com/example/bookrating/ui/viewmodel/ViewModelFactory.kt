package com.example.bookrating.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookrating.data.BooksRepository
import com.example.bookrating.data.RatingsRepository
import com.example.bookrating.rating.NumberGenerator
import com.example.rx.scheduler.SchedulerConfiguration
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val booksRepository: BooksRepository, private val ratingsRepository: RatingsRepository, private val generator: NumberGenerator, private val schedulerConfiguration: SchedulerConfiguration) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(RatingViewModel::class.java) ->
            RatingViewModel(
                booksRepository,
                ratingsRepository,
                generator,
                schedulerConfiguration
            ) as T
        else -> throw  IllegalArgumentException("View model has no support for this type")
    }
}