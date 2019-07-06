package com.example.bookrating.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookrating.data.BooksRepository
import com.example.bookrating.data.RatingsRepository
import com.example.bookrating.model.BookWithRating
import com.example.bookrating.rating.NumberGenerator
import com.example.rx.scheduler.SchedulerConfiguration
import io.reactivex.disposables.CompositeDisposable

class RatingViewModel(
    private val booksRepository: BooksRepository,
    private val ratingsRepository: RatingsRepository,
    private val generator: NumberGenerator,
    private val schedulerConfiguration: SchedulerConfiguration
) : ViewModel() {

    private val ratingLiveData = MutableLiveData<Int>()
    private val booksLiveData = MutableLiveData<List<BookWithRating>>()

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    var generatorActive: Boolean = false

    fun getRatingLiveData(): LiveData<Int> {
        return ratingLiveData
    }

    fun getBooksLiveData(): LiveData<List<BookWithRating>> {
        return booksLiveData
    }

    fun getBooks() {
        val disposable = booksRepository.fetchBooks()
            .subscribe { books ->
                booksLiveData.postValue(books)
            }

        compositeDisposable.add(disposable)
    }

    fun generateRating() {
        val disposable = generator.getNextNumber()
            .subscribeOn(schedulerConfiguration.ui)
            .subscribe({ number ->
                ratingLiveData.postValue(number)
            }, {
                ratingLiveData.postValue(-1)
            })

        compositeDisposable.add(disposable)
    }

    fun stopRating() {
        compositeDisposable.clear()
    }

    fun generatorButtonClicked() {
        if (!generatorActive) {
            generatorActive = true
            generateRating()
        } else {
            generatorActive = false
            stopRating()
        }
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    fun setBookRating(bookId: String, rate: Int) {

    }
}