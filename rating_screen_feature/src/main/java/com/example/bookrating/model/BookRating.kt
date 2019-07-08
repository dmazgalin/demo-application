package com.example.bookrating.model

/**
 * Data class for books ratings
 * @param id Book identifier
 * @param oneStar - amount of 1 star ratings
 * @param twoStar - amount of 2 star ratings
 * @param threeStar - amount of 3 star ratings
 * @param fourStar - amount of 4 star ratings
 * @param fiveStar - amount of 5 star ratings
 */
data class BookRating(val id: String, var oneStar: Int, var twoStar: Int, var threeStar: Int, var fourStar: Int, var fiveStar: Int) {
    constructor(id: String) : this(id, 0, 0, 0, 0, 0)

    /**
     *  Get everage rating by calculating total amount of rate values divided by total amout of rates
     */
    fun getEverageRating(): Int {
        return (1 * oneStar + 2 * twoStar + 3 * threeStar + 4 * fourStar + 5 * fiveStar) / (oneStar + twoStar + threeStar + fourStar + fiveStar)
    }

    /**
     * Add new rating to the book from 1..5
     * @param rate new rating for item
     */
    fun addNewRating(rate: Int) {
        when (rate) {
            1 -> oneStar++
            2 -> twoStar++
            3 -> threeStar++
            4 -> fourStar++
            5 -> fiveStar++
        }
    }
}