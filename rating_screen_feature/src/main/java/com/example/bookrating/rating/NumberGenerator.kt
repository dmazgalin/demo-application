package com.example.bookrating.rating

import com.example.bookrating.model.Book
import com.example.bookrating.model.BookWithRating
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class NumberGenerator(val scheduler: Scheduler) {

    fun getNextNumber(books: List<Book>): Observable<GenerationResult> {
        return Observable.interval(3000, TimeUnit.MILLISECONDS, scheduler)
            .map { GenerationResult(books.get(Random.nextInt(books.size)), Random.nextInt(5)) }
    }
}