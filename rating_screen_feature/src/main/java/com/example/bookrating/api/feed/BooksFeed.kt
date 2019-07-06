package com.example.bookrating.api.feed

data class BooksFeed(val items: List<BookFeed>) {
    constructor() : this(emptyList())
}