package com.example.bookrating.model

data class BookRating(val id: String, var oneStart: Int, var twoStart: Int, var threeStar: Int, var fourStar: Int, var fiveStar: Int) {
    constructor(id: String) : this(id, 0, 0, 0, 0, 0)

    fun getEverageRating(): Int {
        return (1 * oneStart + 2 * twoStart + 3 * threeStar + 4 * fourStar + 5 * fiveStar) / (oneStart + twoStart + threeStar + fourStar + fiveStar)
    }

    fun addNewRating(rate: Int) {
        when (rate) {
            1 -> oneStart++
            2 -> twoStart++
            3 -> threeStar++
            4 -> fourStar++
            5 -> fiveStar++
        }
    }
}