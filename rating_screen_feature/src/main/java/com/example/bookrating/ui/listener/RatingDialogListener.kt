package com.example.bookrating.ui.listener

interface RatingDialogListener {
    fun onPositiveButtonClicked(rate: Int, bookId: String)
    fun onNegativeButtonClicked()
}