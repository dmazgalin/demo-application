package com.example.bookrating.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookrating.R
import com.example.bookrating.ui.dialog.RatingDialog
import com.example.bookrating.ui.listener.RatingDialogListener

const val RATING_DIALOG = "RatingDialog"

class BooksActivity: AppCompatActivity(), RatingDialogListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.books_activity)

        show("1")
    }

    /**
     * This method shows rating dialog.
     */
    fun show(bookId: String) {
        RatingDialog.newInstance(bookId).apply {
            setListener(this@BooksActivity)
            show(supportFragmentManager, RATING_DIALOG)
        }
    }

    override fun onPositiveButtonClicked(rate: Int, bookId: String) {
    }

    override fun onNegativeButtonClicked() {
    }
}