package com.example.bookrating.data

import com.example.bookrating.api.feed.BookRating
import java.util.HashMap

class RatingsRepositoryImpl: RatingsRepository {

    private val ratings = HashMap<String, BookRating>()

    override fun getBookRating(id: String): BookRating? {
        return ratings.get(id)
    }

    override fun addRating(bookId: String, rate: Int) {
        if (rate in 1..5) {
            val currentRating: BookRating

            if (ratings.containsKey(bookId)) {
                currentRating = ratings.getValue(bookId)
                currentRating.addNewRating(rate)
            } else {
                currentRating = BookRating(bookId)
                currentRating.addNewRating(rate)
            }

            ratings.put(bookId, currentRating)
        }
    }

    override fun clear() {
        ratings.clear()
    }
}