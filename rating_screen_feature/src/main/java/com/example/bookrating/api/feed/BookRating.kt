package com.example.bookrating.api.feed

data class BookRating(val id: String, val oneStart: Int, val twoStart: Int, val threeStar: Int, val fourStar: Int, val fiveStar: Int) {
    constructor(id: String) : this(id, 0,0,0,0,0)
}