package com.example.bookrating.rating

import com.example.bookrating.data.BooksRepository
import com.example.bookrating.model.Book
import com.example.rx.test.TestSchedulerConfigurationImpl
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class NumberGeneratorTest {

    @Mock
    lateinit var booksRepository: BooksRepository

    lateinit var book: Book
    lateinit var generator: NumberGenerator
    private val disposables: CompositeDisposable = CompositeDisposable()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        generator = NumberGenerator(TestSchedulerConfigurationImpl.schedulerConfiguration.test)
    }

    @Test
    fun testNumbersAreGenerated() {

        book = createSampleBook()

        whenever(booksRepository.getBooks()).thenReturn(listOf(book))

        val testObserver = generator.getNextNumber(booksRepository.getBooks()).test()

        disposables.add(testObserver)

        (generator.scheduler as TestScheduler).advanceTimeBy(10000, TimeUnit.MILLISECONDS)

        testObserver.assertSubscribed().assertNoErrors()

        val items = testObserver.values()

        assertTrue(items.isNotEmpty(), "Items should be generated")

        for (item in items) {
            assert(item.rating < 5)
        }
    }

    @After
    fun tearDown() {
        disposables.clear()
    }

    private fun createSampleBook(): Book {
        return Book("1", "Book", "image")
    }
}