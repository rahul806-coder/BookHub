package com.rahul.bookhub.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.rahul.BookHub.R
import com.rahul.bookhub.model.Book
import com.rahul.bookhub.start.DescriptionActivity
import com.squareup.picasso.Picasso
import java.util.ArrayList

class DashboardRecyclerAdapter(val context:Context,val itemList:ArrayList<Book>): RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>(){
    class DashboardViewHolder(view:View):RecyclerView.ViewHolder(view)
    {
        val txtBookName:TextView=view.findViewById(R.id.txtnameBook)
        val txtBookRating:TextView=view.findViewById(R.id.txtRating)
        val txtBookAuthor:TextView=view.findViewById(R.id.txtAuthorName)
        val txtBookPrice:TextView=view.findViewById(R.id.txtPrice)
        val imgBookImage:ImageView=view.findViewById(R.id.imgBookImg)
        val llContent: RelativeLayout=view.findViewById(R.id.llContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
          val book=itemList[position]
        holder.txtBookName.text=book.bookName
        holder.txtBookAuthor.text=book.bookAuthor
        holder.txtBookRating.text=book.bookRating
        holder.txtBookPrice.text=book.bookPrice
       // holder.imgBookImage.setImageResource(book.bookImage)
        Picasso.get().load(book.bookImage).error(R.drawable.book_splash_1).into(holder.imgBookImage)
        holder.llContent.setOnClickListener{
            val intent= Intent(context, DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}