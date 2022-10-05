package com.rahul.bookhub.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rahul.BookHub.R
import com.rahul.bookhub.database.BookEntity
import com.squareup.picasso.Picasso
import android.widget.*

class FavouriteRecyclerAdapter(val context: Context, val bookList: List<BookEntity>) :
        RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_favourite_single_row, parent, false)

        return FavouriteViewHolder(view)

    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val book = bookList[position]

        holder.txtBookName.text = book.bookName
        holder.txtBookAuthor.text = book.bookAuthor
        holder.txtBookRating.text = book.bookRating
        holder.txtBookPrice.text = book.bookPrice
        Picasso.get().load(book.bookImage).error(R.drawable.book_splash_1)
                .into(holder.imgBookImage)


    }

    class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtBookName: TextView = view.findViewById(R.id.txtFavBookTitle)
        val txtBookAuthor: TextView = view.findViewById(R.id.txtFavBookAuthor)
        val txtBookRating: TextView = view.findViewById(R.id.txtFavBookRating)
        val txtBookPrice: TextView = view.findViewById(R.id.txtFavBookPrice)
        val imgBookImage: ImageView = view.findViewById(R.id.imgFavBookImage)
        val llcontent: LinearLayout = view.findViewById(R.id.llFavContent)

    }


}



