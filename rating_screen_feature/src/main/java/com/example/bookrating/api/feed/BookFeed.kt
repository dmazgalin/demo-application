package com.example.bookrating.api.feed

/**
 * Feed object for the book received from api
 * @param id - book id
 * @param title - book title
 * @param image - book image
 */
data class BookFeed(val id: String, val title: String, val image: String)