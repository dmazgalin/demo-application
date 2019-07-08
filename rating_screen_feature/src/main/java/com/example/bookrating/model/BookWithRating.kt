package com.example.bookrating.model

/**
 * Data class for book with rating
 * @param id - book id
 * @param title - book title
 * @param image - book image (not used at the moment future work for Picasso)
 * @param rating - book avarage rating
 */
data class BookWithRating(val id: String, val title: String, val image: String, var rating: Int)