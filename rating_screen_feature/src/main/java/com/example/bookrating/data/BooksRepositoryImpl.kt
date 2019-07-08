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

    override fun getBooks(): Observable<List<Book>> {
        val network = Observable.fromCallable {
            fetchBooksFromApi()
        }.observeOn(schedulers.io)
            .map { BooksParser().parse(it) }
            .filter { list -> !list.isEmpty() }
            .doOnNext { list -> addToCache(list) }

        val cache = Observable.just(bookCache)
            .filter { list ->
                !list.isEmpty()
            }

        return Observable.concat(cache, network)
            .firstElement()
            .toObservable()
    }

    private fun fetchBooksFromApi() = api.fetchBooks()

    private fun addToCache(it: List<Book>) {
        with(bookCache) {
            clear()
            addAll(it)
        }
    }
}