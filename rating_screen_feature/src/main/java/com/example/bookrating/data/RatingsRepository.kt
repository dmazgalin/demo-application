package com.example.bookrating.data

import com.example.bookrating.model.BookRating

/**
 * Rating repository interface
 */
interface RatingsRepository {
    /**
     * get book rating from repository
     * @param bookId - book id
     * @return book rating object
     */
    fun getBookRating(bookId: String): BookRating?

    /**
     * add book rating to repository
     * @param bookId book id
     * @param rate book rating with range 1..5
     */
    fun addRating(bookId: String, rate: Int)

    /**
     * Clear all ratings
     */
    fun clear()
}