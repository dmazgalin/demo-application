package com.example.bookrating.api.parser

import com.example.bookrating.api.feed.BooksFeed
import com.example.bookrating.model.Book
import com.example.bookrating.model.BookWithRating

class BooksParser {
    fun parse(feed: BooksFeed): List<Book> {
        val result = mutableListOf<Book>()
        feed.items.forEach {
            result.add(Book(it.id, it.title, it.image))
        }
        return result
    }
}