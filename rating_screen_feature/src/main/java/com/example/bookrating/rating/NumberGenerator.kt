package com.example.bookrating.rating

import com.example.bookrating.model.Book
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * Generator to generate book rating. Runs every 3 second and returns numbers 0..5.
 * 0- means no rating, 1..5 - new rating
 */
class NumberGenerator(val scheduler: Scheduler) {

    /**
     * Get next number for rating
     * @param books - List of books to generate rating for
     * @return - Observable with rating generation result
     */
    fun getRatingGeneration(books: List<Book>): Observable<GenerationResult> {
        return Observable.just(true)
            .map {
                GenerationResult(books[Random.nextInt(books.size)], Random.nextInt(MAX_RATING_VALUE))
            }.repeatWhen {
                it.delay(periods[Random.nextInt(periods.size)], TimeUnit.SECONDS)
            }
            .subscribeOn(scheduler)
    }

    companion object {
        private const val MAX_RATING_VALUE = 5
        private val periods: LongArray = longArrayOf(3, 4, 5, 6, 7)
    }
}