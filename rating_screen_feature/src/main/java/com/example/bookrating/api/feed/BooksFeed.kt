package com.example.bookrating.api.feed

/**
 * Feed object for list of books received from api
 * @param items - list of books
 */
data class BooksFeed(val items: List<BookFeed>) {
    constructor() : this(emptyList())
}