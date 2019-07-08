package com.example.bookrating.data

import com.example.bookrating.model.Book
import com.example.bookrating.model.BookWithRating
import io.reactivex.Observable

/**
 * Books repository interface
 */
interface BooksRepository {
    /**
     * get books from repository
     * @return Observable with list of books
     */
    fun getBooks() : Observable<List<Book>>
}