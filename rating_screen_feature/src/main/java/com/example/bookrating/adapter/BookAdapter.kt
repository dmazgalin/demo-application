package com.example.bookrating.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bookrating.R
import com.example.bookrating.adapter.utils.BooksDiffUtils
import com.example.bookrating.adapter.viewholder.BookViewHolder
import com.example.bookrating.model.BookWithRating
import java.math.RoundingMode
import java.text.DecimalFormat

class BookAdapter(private val onClickListener: (BookWithRating) -> Unit) : RecyclerView.Adapter<BookViewHolder>() {

    private var books: List<BookWithRating> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.book_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val bookWithRating = books.getOrNull(position)
        bookWithRating?.let { item ->
            bindBookToViewHolder(holder, item)
        }
    }

    private fun bindBookToViewHolder(holder: BookViewHolder, item: BookWithRating) {
        with(holder) {
            title.text = item.title
            rating.text = roundOffDecimal(itemView.resources, item.rating)
            itemView.setOnClickListener { onClickListener(item) }
        }
    }

    fun setBooks(list: List<BookWithRating>) {
        val diffUtils = BooksDiffUtils(books, list)
        val diffResult = DiffUtil.calculateDiff(diffUtils)

        diffResult.dispatchUpdatesTo(this)

        this.books = list
    }

    private fun roundOffDecimal(resources: Resources, number: Float): String {
        if (number > 0) {
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.FLOOR
            return resources.getString(R.string.rating, df.format(number))
        }
        return resources.getString(R.string.no_rating)
    }
}