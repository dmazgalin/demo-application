package com.example.bookrating.rating

import com.example.bookrating.model.Book

/**
 * Data class for automatic rating generation result
 * @param book - book for which rating was generated
 * @param rating - automatically generated rating
 */
data class GenerationResult(val book: Book, val rating: Int)