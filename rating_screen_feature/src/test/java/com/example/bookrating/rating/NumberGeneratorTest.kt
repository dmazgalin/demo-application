package com.example.bookrating.rating

import com.example.rx.test.TestSchedulerConfigurationImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class NumberGeneratorTest {

    lateinit var generator: NumberGenerator
    private val disposables: CompositeDisposable = CompositeDisposable()

    @Before
    fun setUp() {
        generator = NumberGenerator(TestSchedulerConfigurationImpl.schedulerConfiguration.test)
    }

    @Test
    fun testNumbersAreGenerated() {

        val testObserver = generator.getNextNumber().test()

        disposables.add(testObserver)

        (generator.scheduler as TestScheduler).advanceTimeBy(10000, TimeUnit.MILLISECONDS)

        testObserver.assertSubscribed().assertNoErrors()

        val items = testObserver.values()

        assertTrue(items.isNotEmpty(), "Items should be generated")

        for (item in items) {
            assert(item < 5)
        }
    }

    @After
    fun tearDown() {
        disposables.clear()
    }
}