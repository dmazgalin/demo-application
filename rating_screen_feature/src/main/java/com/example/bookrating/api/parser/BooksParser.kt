package com.example.bookrating.api.parser

import com.example.bookrating.api.feed.BooksFeed
import com.example.bookrating.model.Book
import com.example.bookrating.model.BookWithRating

/**
 * Parser for books received from API
 */
class BooksParser {
    /**
     * Return list of books parsed from feed object
     * @param feed - books feed from API
     * @return - list of books
     */
    fun parse(feed: BooksFeed): List<Book> {
        val result = mutableListOf<Book>()
        feed.items.forEach {
            result.add(Book(it.id, it.title, it.image))
        }
        return result
    }
}