package com.example.bookrating.api

import com.example.bookrating.api.feed.BookFeed
import com.example.bookrating.api.feed.BooksFeed

class BooksApi {

    val booksList = mutableListOf<BookFeed>()

    init {
        booksList.add(BookFeed("1", "Book 1", ""))
        booksList.add(BookFeed("2", "Book 2", ""))
        booksList.add(BookFeed("3", "Book 3", ""))
        booksList.add(BookFeed("4", "Book 4", ""))
        booksList.add(BookFeed("5", "Book 5", ""))
        booksList.add(BookFeed("6", "Book 6", ""))
        booksList.add(BookFeed("7", "Book 7", ""))
        booksList.add(BookFeed("8", "Book 8", ""))
        booksList.add(BookFeed("9", "Book 9", ""))
        booksList.add(BookFeed("10", "Book 10", ""))
        booksList.add(BookFeed("11", "Book 11", ""))
        booksList.add(BookFeed("12", "Book 12", ""))
    }

    fun fetchBooks(): BooksFeed {
        return BooksFeed(booksList)
    }
}