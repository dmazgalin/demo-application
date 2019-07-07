package com.example.bookrating.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookrating.data.BooksRepository
import com.example.bookrating.data.RatingsRepository
import com.example.bookrating.model.Book
import com.example.bookrating.model.BookWithRating
import com.example.bookrating.rating.GenerationResult
import com.example.bookrating.rating.NumberGenerator
import com.example.rx.scheduler.SchedulerConfiguration
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class BooksActivityViewModel(
    private val booksRepository: BooksRepository,
    private val ratingsRepository: RatingsRepository,
    private val generator: NumberGenerator,
    private val schedulerConfiguration: SchedulerConfiguration
) : ViewModel() {

    private val ratingLiveData = MutableLiveData<GenerationResult>()
    private val booksLiveData = MutableLiveData<List<BookWithRating>>()
    private val dialogLiveData = MutableLiveData<BookWithRating>()
    private val controllButtonLiveData = MutableLiveData<Boolean>()

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var generatorActive: Boolean = false

    fun getRatingLiveData(): LiveData<GenerationResult> = ratingLiveData

    fun getBooksLiveData(): LiveData<List<BookWithRating>> = booksLiveData

    fun getDialogCallLiveData(): LiveData<BookWithRating> = dialogLiveData

    fun getControlButtonLiveData(): LiveData<Boolean> = controllButtonLiveData

    fun getBooks() {
        val disposable = booksRepository.fetchBooks()
            .map {
                applyRating(it, ratingsRepository)
            }
            .map { it ->
                it.sortedByDescending { it.rating }
            }
            .observeOn(schedulerConfiguration.ui)
            .subscribe { books ->
                booksLiveData.postValue(books)
            }

        compositeDisposable.add(disposable)
    }

    private fun applyRating(books: List<Book>, ratingsRepository: RatingsRepository): List<BookWithRating> {

        val booksWithRating = mutableListOf<BookWithRating>()
        for (book in books) {
            val rating = ratingsRepository.getBookRating(book.id)?.getEverageRating() ?: 0
            booksWithRating.add(BookWithRating(book.id, book.title, book.image, rating))
        }

        return booksWithRating
    }

    fun generateRating() {
        val disposable = generator.getNextNumber(booksRepository.getBooks())
            .observeOn(schedulerConfiguration.ui)
            .subscribe({ result ->
                run {
                    ratingsRepository.addRating(result.book.id, result.rating)
                    ratingLiveData.postValue(result)
                }
            }, {
                Timber.e(it, "generateRating()")
            })

        compositeDisposable.add(disposable)
    }

    private fun stopRating() {
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
        controllButtonLiveData.postValue(generatorActive)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    fun setBookRating(bookId: String, rate: Int) {
        ratingsRepository.addRating(bookId, rate)

        getBooks()
    }

    fun onItemClick(position: Int, bookWithRating: BookWithRating) {
        dialogLiveData.postValue(bookWithRating)
    }
}