package com.example.bookrating.data

import com.example.bookrating.api.BooksApi
import com.example.bookrating.api.parser.BooksParser
import com.example.bookrating.model.Book
import com.example.rx.scheduler.SchedulerConfiguration
import io.reactivex.Observable

class BooksRepositoryImpl(
    private val api: BooksApi,
    private val schedulers: SchedulerConfiguration
) : BooksRepository {

    val bookCache = mutableListOf<Book>()

    override fun fetchBooks(): Observable<List<Book>> {
        val network = Observable.fromCallable {
            api.fetchBooks()
        }.observeOn(schedulers.io)
            .map { BooksParser().parse(it) }
            .filter { list -> !list.isEmpty() }
            .doOnNext { list -> addToCache(list) }

        val cache = Observable.just(bookCache).filter { list -> !list.isEmpty() }

        return Observable.concat(cache, network).firstElement().toObservable()
    }

    override fun getBooks(): List<Book> {
        return bookCache
    }

    private fun addToCache(it: List<Book>) {
        bookCache.clear()
        bookCache.addAll(it)
    }
}