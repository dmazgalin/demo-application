package com.example.bookrating.data

import com.example.bookrating.model.BookWithRating
import io.reactivex.Observable

interface BooksRepository {
    fun fetchBooks() : Observable<List<BookWithRating>>
}