package com.example.bookrating.data

import com.example.bookrating.api.BooksApi
import com.example.bookrating.api.feed.BookFeed
import com.example.bookrating.api.feed.BooksFeed
import com.example.rx.test.TestSchedulerConfigurationImpl
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BooksRepositoryTest {

    @Mock
    lateinit var api: BooksApi

    private val disposables: CompositeDisposable = CompositeDisposable()

    //SUT
    private lateinit var booksRepository: BooksRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        booksRepository = BooksRepositoryImpl(api, TestSchedulerConfigurationImpl.schedulerConfiguration)
    }

    @Test
    fun testRepoCanParseEmptyBooksFeed() {
        whenever(api.fetchBooks()) doReturn BooksFeed()

        val testObserver = booksRepository.fetchBooks().test()

        disposables.add(testObserver)

        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors().assertValue({ l -> l.size == 0 })
    }

    @Test
    fun testRepoCanParseNonEmptyBooksFeed() {
        whenever(api.fetchBooks()) doReturn getNonEmptyBooksFeed()

        val testObserver = booksRepository.fetchBooks().test()

        disposables.add(testObserver)

        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors().assertValue({ l -> l.size == 3 })
    }

    private fun getNonEmptyBooksFeed() = BooksFeed(listOf(getBook(1), getBook(2), getBook(3)))

    private fun getBook(id: Int) = BookFeed("$id", "Book $id", "")

    @After
    fun tearDown() {
        disposables.dispose()
    }
}