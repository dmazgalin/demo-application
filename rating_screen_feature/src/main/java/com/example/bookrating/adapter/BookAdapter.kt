package com.example.bookrating.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bookrating.R
import com.example.bookrating.adapter.utils.BooksDiffUtils
import com.example.bookrating.model.BookWithRating
import com.example.bookrating.adapter.viewholder.BookViewHolder
import java.util.ArrayList

class BookAdapter : RecyclerView.Adapter<BookViewHolder>() {

    private var books: List<BookWithRating> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.book_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.title.text = books.get(position).title
        holder.rating.text = books.get(position).rating.toString()
    }

    fun setBooks(list: List<BookWithRating>) {
        val diffUtils = BooksDiffUtils(books, list)
        val diffResult = DiffUtil.calculateDiff(diffUtils)

        diffResult.dispatchUpdatesTo(this)

        this.books = list
    }
}