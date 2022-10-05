package com.rahul.bookhub.start

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.rahul.BookHub.R
import com.rahul.bookhub.Adapter.DashboardRecyclerAdapter
import com.rahul.bookhub.database.BookDatabase
import com.rahul.bookhub.database.BookEntity
import com.rahul.bookhub.model.Book
import com.rahul.bookhub.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {

    lateinit var txtnameBook: TextView
    lateinit var txtAuthorName: TextView
    lateinit var txtPrice: TextView
    lateinit var txtRating: TextView
    lateinit var imgBookImg: ImageView
    lateinit var txtBookDesc: TextView
    lateinit var btnAddToFav: Button
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    lateinit var toolbar: Toolbar

    var bookId: String? ="100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        txtnameBook = findViewById(R.id.txtnameBook)
        txtAuthorName = findViewById(R.id.txtAuthorName)
        txtPrice = findViewById(R.id.txtPrice)
        txtRating = findViewById(R.id.txtRating)
        imgBookImg = findViewById(R.id.imgBookImg)
        txtBookDesc = findViewById(R.id.txtBookDesc)
        btnAddToFav = findViewById(R.id.btnAddToFav)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Detail"

        if (intent != null) {
            bookId = intent.getStringExtra("book_id")
        } else {
            finish()
            Toast.makeText(this@DescriptionActivity, "Some unexpected error occured !!!", Toast.LENGTH_SHORT).show()
        }

        if (bookId == "100") {
            finish()
            Toast.makeText(this@DescriptionActivity, "Some unexpected error occured !!!", Toast.LENGTH_SHORT).show()
        }

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"

        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookId)

        if(ConnectionManager().checkConnectivity(this@DescriptionActivity))
        {
            val jsonRequest =
            object : JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {

                try {

                    val success = it.getBoolean("success")
                    if (success) {
                        progressLayout.visibility = View.GONE
                        val bookJsonObject= it.getJSONObject("book_data")


                        val bookImageUrl = bookJsonObject.getString("image")
                        Picasso.get().load(bookJsonObject.getString("image")).error(R.drawable.food_runner_logo).into(imgBookImg)
                        txtnameBook.text = bookJsonObject.getString("name")
                        txtAuthorName.text = bookJsonObject.getString("author")
                        txtRating.text = bookJsonObject.getString("rating")
                        txtPrice.text = bookJsonObject.getString("price")
                        txtBookDesc.text = bookJsonObject.getString("description")

                        val bookEntity = BookEntity(

                            bookId?.toInt() as Int,
                            txtnameBook.text.toString(),
                            txtAuthorName.text.toString(),
                            txtRating.text.toString(),
                            txtPrice.text.toString(),
                            txtBookDesc.text.toString(),
                            bookImageUrl

                        )

                        val checkFav = DBAsyncTask(applicationContext, bookEntity, 1).execute()
                        val isFav= checkFav.get()

                        if(isFav)
                        {
                            btnAddToFav.text = "Remove from Favourites"
                            val favcolor = ContextCompat.getColor(applicationContext, R.color.dark_blue)
                            btnAddToFav.setBackgroundColor(favcolor)
                        }else
                        {
                            btnAddToFav.text = "Add to Favourites"
                            val nofavcolor = ContextCompat.getColor(applicationContext, R.color.red_orange)
                            btnAddToFav.setBackgroundColor(nofavcolor)
                        }

                        btnAddToFav.setOnClickListener {
                            if(!DBAsyncTask(
                                            applicationContext,
                                            bookEntity,
                                            1
                            ).execute().get()
                            ) {

                                val async= DBAsyncTask(applicationContext, bookEntity, 2).execute()
                                val result= async.get()
                                if(result)
                                {
                                    Toast.makeText(this@DescriptionActivity, "Book added To Favourites", Toast.LENGTH_SHORT).show()

                                    btnAddToFav.text = "Remove from Favourites"
                                    val favcolor = ContextCompat.getColor(applicationContext, R.color.dark_blue)
                                    btnAddToFav.setBackgroundColor(favcolor)
                                }else
                                {
                                    Toast.makeText(this@DescriptionActivity, "Some error occurred", Toast.LENGTH_SHORT).show()
                                }
                            }else
                            {
                                val async= DBAsyncTask(applicationContext, bookEntity, 3).execute()
                                val result= async.get()
                                if(result)
                                {
                                    Toast.makeText(this@DescriptionActivity, "Book removed from Favourites", Toast.LENGTH_SHORT).show()
                                    btnAddToFav.text = "Add to Favourites"
                                    val nofavcolor = ContextCompat.getColor(applicationContext, R.color.red_orange)
                                    btnAddToFav.setBackgroundColor(nofavcolor)
                                }else
                                {
                                    Toast.makeText(this@DescriptionActivity, "Some error occurred", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    } else {
                        Toast.makeText(this@DescriptionActivity, "Some error occurred", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    Toast.makeText(this@DescriptionActivity, "Some unexpected Error occurred !!!", Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener {
                // println("Error is $it")
                Toast.makeText(this@DescriptionActivity, "Volley Error occurred !!!", Toast.LENGTH_SHORT).show()
            }) {
                override fun getHeaders(): MutableMap<String, String> {

                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "56786568e0dc91"
                    return headers
                }
            }
            queue.add(jsonRequest)

        }else
        {
            val dialog= AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Error")
            dialog.setMessage(" No Internet Connection Found")
            dialog.setPositiveButton("Open Setting")
            {
                text,listner->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                finish()
            }
            dialog.setNegativeButton("Exit"){
                text,listner->
                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }
            dialog.create()
            dialog.show()
        }


    }

    class DBAsyncTask(val context: Context, val bookEntity: BookEntity, val mode: Int) : AsyncTask<Void, Void, Boolean>() {

        val db=Room.databaseBuilder(context, BookDatabase::class.java, "books-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {

            when(mode)
            {
                1-> {
                    val book: BookEntity?= db.bookDao().getBookById(bookEntity.book_id.toString())
                    db.close()
                    return book !=null
                }

                2->{

                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }

                3->{
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true
                }
            }
            return false
        }

    }
}