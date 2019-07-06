package com.example.bookrating.ui.viewmodel

import com.example.bookrating.data.BooksRepository
import com.example.bookrating.data.RatingsRepository
import com.example.bookrating.data.RatingsRepositoryTest
import com.example.bookrating.rating.NumberGenerator
import com.example.bookrating.ui.viewmodel.RatingViewModel
import com.example.rx.scheduler.SchedulerConfiguration
import com.example.rx.test.TestSchedulerConfigurationImpl
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.Scheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class RatingViewModelTest {

    @Mock
    lateinit var booksRepository: BooksRepository
    @Mock
    lateinit var ratingsRepository: RatingsRepository
    @Mock
    lateinit var ratingGenerator: NumberGenerator

    val schedulerConfiguration = TestSchedulerConfigurationImpl.schedulerConfiguration

    //SUT
    lateinit var viewModel: RatingViewModel

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        viewModel = RatingViewModel(booksRepository, ratingsRepository, ratingGenerator, schedulerConfiguration)
    }

    @Test
    fun generatorButtonClick() {

        whenever(ratingGenerator.getNextNumber()).thenReturn(Observable.just(1))

        viewModel.generatorButtonClicked()

        verify(ratingGenerator, only()).getNextNumber()
    }
}