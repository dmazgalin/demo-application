package com.example.bookrating.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookrating.R
import com.example.bookrating.adapter.BookAdapter
import com.example.bookrating.dagger.Injector
import com.example.bookrating.ui.dialog.RatingDialog
import com.example.bookrating.ui.listener.RatingDialogListener
import com.example.bookrating.ui.viewmodel.RatingViewModel
import com.example.bookrating.ui.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.books_activity.*
import timber.log.Timber
import javax.inject.Inject

const val RATING_DIALOG = "RatingDialog"

class BooksActivity : AppCompatActivity(), RatingDialogListener {

    lateinit var bookAdapter: BookAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[RatingViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.books_activity)

        bookAdapter = BookAdapter()

        booksRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        booksRecycler.adapter = bookAdapter

        viewModel.getBooksLiveData().observe(this, Observer { books ->
            bookAdapter.setBooks(books)
        })

        viewModel.getRatingLiveData().observe(this, Observer { rating ->
            run {
                Toast.makeText(this, "Rating is $rating", Toast.LENGTH_SHORT).show()

                getBooks()
            }
        })

        generatorButton.setOnClickListener { v ->
            run {
                viewModel.generatorButtonClicked()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        getBooks()
    }

    override fun onPositiveButtonClicked(rate: Int, bookId: String) {
        Timber.d("Received new rating $rate for book with id $bookId")
        viewModel.setBookRating(bookId, rate)
    }

    override fun onNegativeButtonClicked() {
        Timber.d("Rating was canceled")
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

    private fun getBooks() {
        viewModel.getBooks()
    }

}