package com.example.bookrating.data

import com.example.bookrating.model.BookRating

interface RatingsRepository {
    fun getBookRating(id: String): BookRating?
    fun addRating(bookId: String, rate: Int)
    fun clear()
}