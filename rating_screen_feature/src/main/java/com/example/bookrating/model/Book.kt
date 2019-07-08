package com.example.bookrating.model

/**
 * Data class for Book
 * @param id - book id
 * @param title - book title
 * @param image - book image (not used at the moment future work for Picasso)
 */
data class Book(val id: String, val title: String, val image: String)