package com.example.bookrating.dagger.module

import com.example.bookrating.api.BooksApi
import com.example.bookrating.data.BooksRepository
import com.example.bookrating.data.BooksRepositoryImpl
import com.example.bookrating.data.RatingsRepository
import com.example.bookrating.data.RatingsRepositoryImpl
import com.example.bookrating.ui.viewmodel.ViewModelFactory
import com.example.rx.scheduler.SchedulerConfiguration
import dagger.Module
import dagger.Provides

@Module
class RatingFeatureModule {

    @Provides
    fun providesBooksApi(): BooksApi {
        return BooksApi()
    }

    @Provides
    fun providesBooksRepository(api: BooksApi, schedulerConfiguration: SchedulerConfiguration): BooksRepository {
        return BooksRepositoryImpl(api, schedulerConfiguration)
    }

    @Provides
    fun providesRatingsRepository(): RatingsRepository {
        return RatingsRepositoryImpl()
    }

    @Provides
    fun providesViewModelFactory(booksRepository: BooksRepository, repository: RatingsRepository, schedulerConfiguration: SchedulerConfiguration): ViewModelFactory {
        return ViewModelFactory(booksRepository, repository, schedulerConfiguration)
    }
}