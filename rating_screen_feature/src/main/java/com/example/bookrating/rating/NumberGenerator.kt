package com.example.bookrating.rating

import com.example.bookrating.model.Book
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * Generator to generat book rating. Runs every 3 second and returns numbers 0..5.
 * 0- means no rating, 1..5 - new rating
 */
class NumberGenerator(val scheduler: Scheduler) {

    /**
     * get next number for rating
     * @param books - list of books to generate rating for
     * @return - rating generation result
     */
    fun getNextNumber(books: List<Book>): Observable<GenerationResult> {
        return Observable.interval(PERION, TimeUnit.MILLISECONDS, scheduler)
            .map { GenerationResult(books.get(Random.nextInt(books.size)), Random.nextInt(MAX_RATING_VALUE)) }
    }

    companion object {
        private const val PERION = 3000L
        private const val MAX_RATING_VALUE = 5
    }
}