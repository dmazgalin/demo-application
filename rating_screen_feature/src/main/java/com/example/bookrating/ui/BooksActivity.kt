package com.example.bookrating.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.bookrating.R
import com.example.bookrating.dagger.Injector
import com.example.bookrating.ui.dialog.RatingDialog
import com.example.bookrating.ui.listener.RatingDialogListener
import com.example.bookrating.ui.viewmodel.RatingViewModel
import com.example.bookrating.ui.viewmodel.ViewModelFactory
import javax.inject.Inject

const val RATING_DIALOG = "RatingDialog"

class BooksActivity: AppCompatActivity(), RatingDialogListener{


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[RatingViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.inject(this)
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
        viewModel.setBookRating(bookId, rate)
    }

    override fun onNegativeButtonClicked() {
    }
}