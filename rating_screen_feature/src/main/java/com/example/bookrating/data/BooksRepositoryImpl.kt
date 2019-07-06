package com.example.bookrating.data

import com.example.bookrating.api.BooksApi
import com.example.bookrating.api.parser.BooksParser
import com.example.bookrating.model.BookWithRating
import com.example.rx.scheduler.SchedulerConfiguration
import io.reactivex.Observable

class BooksRepositoryImpl(
    private val api: BooksApi,
    private val schedulers: SchedulerConfiguration
) : BooksRepository {
    override fun fetchBooks(): Observable<List<BookWithRating>> {
        return Observable.fromCallable {
            api.fetchBooks()
        }.observeOn(schedulers.io)
            .map { BooksParser().parse(it) }
            .map {
                it -> it.sortedBy { it.rating }
            }
    }
}