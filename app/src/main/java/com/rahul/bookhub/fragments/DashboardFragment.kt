 package com.rahul.bookhub.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.rahul.BookHub.R
import com.rahul.bookhub.Adapter.DashboardRecyclerAdapter
import com.rahul.bookhub.model.Book
import com.rahul.bookhub.util.ConnectionManager
import org.json.JSONException


 class DashboardFragment : Fragment() {
    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
   lateinit var progressLayout: RelativeLayout
   lateinit var progressBar: ProgressBar

    lateinit var recyclerAdapter: DashboardRecyclerAdapter


    val bookInfoList= arrayListOf<Book>()/*(
             Book("1","PS I Love you","Cecelia Ahern","4.5","Rs. 299",R.drawable.ps_i_love_u),
             Book("2","The Great Gatsby","F.Scott Fitzgerald","4.1","Rs. 399",R.drawable.great_gatsby),
             Book("3","Anna Karenina","Leo Tolstoy","4.3","Rs. 199",R.drawable.anna_kare),
             Book("4","Madame Bovary","Gustave Flaubert","4.0","Rs. 500",R.drawable.madame),
             Book("5","War and Peace","Leo Tolstoy","4.8","Rs. 249",R.drawable.war_peace),
             Book("6","Lolita","Vladimir Nabokov","4.2","Rs. 349",R.drawable.lolita),
             Book("8","Middlemarch","George Elliot","4.5","Rs. 599",R.drawable.middlemarch),
             Book("9","The Adventures of Huckleberry Finn","Mark Twain","4.7","Rs. 699",R.drawable.adventure_finn),
             Book("10","Moby-Dick","Herman Melville","4.5","Rs. 499",R.drawable.moby_dick),
             Book("11","The Lords of the Rings","J.R.R. Tolkien","5.0","Rs. 749",R.drawable.lords_of_ring),

             )*/


     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerDashboard=view.findViewById(R.id.recyclerDashboard)

         progressLayout=view.findViewById(R.id.progressLayout)
         progressBar=view.findViewById(R.id.progressBar)
         progressLayout.visibility= View.VISIBLE



        layoutManager=LinearLayoutManager(activity)


       val queue= Volley.newRequestQueue(activity as Context)
        val url= "http://13.235.250.119/v1/book/fetch_books/"
         if(ConnectionManager().checkConnectivity(activity as Context))
         {
             val jsonObjectRequest=object : JsonObjectRequest(Request.Method.GET,url,null,Response.Listener {

                 try
                 {
                     progressLayout.visibility=View.GONE
                     val success=it.getBoolean("success")
                     if(success)
                     {
                         val data=it.getJSONArray("data")
                         for(i in 0 until data.length())
                         {
                             val bookJSON=data.getJSONObject(i)
                             val bookObject= Book (
                                     bookJSON.getString("book_id"),
                                     bookJSON.getString("name"),
                                     bookJSON.getString("author"),
                                     bookJSON.getString("rating"),
                                     bookJSON.getString("price"),
                                     bookJSON.getString("image")
                             )
                             bookInfoList.add(bookObject)

                             recyclerDashboard.layoutManager=layoutManager

                             recyclerAdapter= DashboardRecyclerAdapter(activity as Context,bookInfoList)
                             recyclerDashboard.adapter=recyclerAdapter

                         }
                     }else
                     {
                         Toast.makeText(activity as Context,"Some error occurred",Toast.LENGTH_SHORT).show()
                     }
                 }catch (e: JSONException){
                     Toast.makeText(activity as Context,"Some unexpected Error occurred !!!",Toast.LENGTH_SHORT).show()
                 }

             },Response.ErrorListener {
                // println("Error is $it")
                 if(activity != null)
                 {
                     Toast.makeText(activity as Context,"Volley Error occurred !!!",Toast.LENGTH_SHORT).show()
                 }

             })
             {
                 override fun getHeaders(): MutableMap<String, String> {

                     val headers= HashMap<String,String>()
                     headers["Content-type"]="application/json"
                     headers["token"]="56786568e0dc91"
                     return headers
                 }
             }
             queue.add(jsonObjectRequest)
         }else
         {
             val dialog=AlertDialog.Builder(activity as Context)
             dialog.setTitle("Error")
             dialog.setMessage(" No Internet Connection Found")
             dialog.setPositiveButton("Open Setting")
             {
                     text,listner->
                 val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                 startActivity(settingIntent)
                 activity?.finish()
             }
             dialog.setNegativeButton("Exit"){
                     text,listner->
                 ActivityCompat.finishAffinity(activity as Activity)
             }
             dialog.create()
             dialog.show()
         }

                return view

    }


}