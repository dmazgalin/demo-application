package com.example.bookrating.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.book_list_item.view.*

class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val title = view.bookTitle
    val image = view.bookImage
    val rating = view.bookRating
}