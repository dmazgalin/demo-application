package com.example.bookrating.adapter.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.bookrating.model.BookWithRating

class BooksDiffUtils(val oldBooks: List<BookWithRating>, val newBooks: List<BookWithRating>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldBooks[oldItemPosition] == newBooks[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldBooks.size
    }

    override fun getNewListSize(): Int {
        return newBooks.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldBooks[oldItemPosition] == newBooks[newItemPosition]
    }
}