package com.example.bookrating.data

import com.example.bookrating.model.Book
import com.example.bookrating.model.BookWithRating
import io.reactivex.Observable

interface BooksRepository {
    fun fetchBooks() : Observable<List<Book>>
    fun getBooks(): List<Book>
}