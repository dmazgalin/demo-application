package com.example.bookrating.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bookrating.model.BookRating
import com.example.bookrating.data.BooksRepository
import com.example.bookrating.data.RatingsRepository
import com.example.bookrating.model.Book
import com.example.bookrating.model.BookWithRating
import com.example.bookrating.rating.GenerationResult
import com.example.bookrating.rating.NumberGenerator
import com.example.rx.test.TestSchedulerConfigurationImpl
import com.example.test.testLiveDataWrapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class BooksActivityViewModelTest {

    @Mock
    lateinit var booksRepository: BooksRepository
    @Mock
    lateinit var ratingsRepository: RatingsRepository
    @Mock
    lateinit var ratingGenerator: NumberGenerator

    private lateinit var bookWithRating: BookWithRating
    private lateinit var book: Book

    private val schedulerConfiguration = TestSchedulerConfigurationImpl.schedulerConfiguration

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //SUT
    private lateinit var viewModel: BooksActivityViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = BooksActivityViewModel(booksRepository, ratingsRepository, ratingGenerator, schedulerConfiguration)

        whenever(ratingsRepository.getBookRating("1")).thenReturn(BookRating("1", 0, 0, 0, 1, 0))

        book = createSampleBook()
        bookWithRating = createSampleBookWithRating()
    }

    @Test
    fun generatorButtonClick() {
        whenever(booksRepository.getBooks()).thenReturn(Observable.just(listOf(book)))
        whenever(ratingGenerator.getRatingGeneration(any())).thenReturn(Observable.just(GenerationResult(book, 4)))
        val dataObserver = viewModel.getControlButtonLiveData().testLiveDataWrapper()

        viewModel.generatorButtonClicked()

        verify(ratingGenerator, only()).getRatingGeneration(any())

        viewModel.generatorButtonClicked()

        assertEquals(2, dataObserver.observedValues.size)

        assertEquals(true, dataObserver.observedValues.first())
        assertEquals(false, dataObserver.observedValues.last())
    }

    @Test
    fun clickOnBookCreateShowDialogEvent() {
        val dataObserver = viewModel.getDialogCallLiveData().testLiveDataWrapper()

        viewModel.onItemClick(0, bookWithRating)

        assertEquals(1, dataObserver.observedValues.size)

        val bookToSetRating = dataObserver.observedValues.first()

        bookToSetRating?.let {
            assertEquals(bookWithRating.id, bookToSetRating.id)
            assertEquals(bookWithRating.title, bookToSetRating.title)
            assertEquals(bookWithRating.image, bookToSetRating.image)
            assertEquals(bookWithRating.rating, bookToSetRating.rating)
        }
    }

    @Test
    fun clickOnSetRatingInDialogCallsAddRatingToRepo() {

        whenever(booksRepository.getBooks()).thenReturn(Observable.just(listOf(book)))

        viewModel.setBookRating("1", 5)

        verify(ratingsRepository, times(1)).addRating("1", 5)
        verify(booksRepository, times(1)).getBooks()
    }

    @Test
    fun getBooksCallsGetBooksToRepo() {
        whenever(booksRepository.getBooks()).thenReturn(Observable.just(listOf(book)))

        val dataObserver = viewModel.getBooksLiveData().testLiveDataWrapper()

        viewModel.getBooks()

        verify(booksRepository, times(1)).getBooks()
        verify(ratingsRepository, times(1)).getBookRating(bookWithRating.id)

        assertEquals(1, dataObserver.observedValues.size)

        val booksFromRepo = dataObserver.observedValues.first()

        booksFromRepo?.let { it ->
            val resultBook = it.firstOrNull()
            resultBook?.let { book ->
                assertEquals(bookWithRating.id, book.id)
                assertEquals(bookWithRating.title, book.title)
                assertEquals(bookWithRating.image, book.image)
                assertEquals(bookWithRating.rating, book.rating)
            }
        }
    }

    private fun createSampleBookWithRating() = BookWithRating("1", "Book", "image", 4)

    private fun createSampleBook() = Book("1", "Book", "image")
}