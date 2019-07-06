package com.example.bookrating.api.parser

import com.example.bookrating.api.feed.BooksFeed
import com.example.bookrating.model.BookWithRating

class BooksParser {
    fun parse(feed: BooksFeed): List<BookWithRating>? {
        val result = mutableListOf<BookWithRating>()
        feed.items.forEach {
            result.add(BookWithRating(it.title, it.image, 0))
        }
        return result
    }
}