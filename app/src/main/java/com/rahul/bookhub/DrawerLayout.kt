package com.rahul.bookhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.rahul.BookHub.R
import com.rahul.bookhub.fragments.AboutAppFragment
import com.rahul.bookhub.fragments.DashboardFragment
import com.rahul.bookhub.fragments.FavouriteFragment
import com.rahul.bookhub.fragments.ProfileFragment

class DrawerLayout : AppCompatActivity() {

    lateinit var drawlayout: DrawerLayout
    lateinit var coordinate: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    var previousMenuItem:MenuItem?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_layout)

        drawlayout=findViewById(R.id.drawer_layout)
        coordinate=findViewById(R.id.coordinate_layout)
        toolbar=findViewById(R.id.toolbarr)
        frameLayout=findViewById(R.id.frame_layout)
        navigationView=findViewById(R.id.navigation)

        setUpToolbar()
        openDashboard()
        val actionBarDrawerToggle=ActionBarDrawerToggle(this@DrawerLayout,
            drawlayout,
            R.string.open_drawer,
            R.string.close_drawer)
        drawlayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem!=null)
            {
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it

            when(it.itemId) {
                R.id.dashboard -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, DashboardFragment())

                            .commit()
                    supportActionBar?.title="Dashboard"
                    drawlayout.closeDrawers()
                }
                R.id.fav -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, FavouriteFragment())

                            .commit()
                    supportActionBar?.title="Favourite Books"
                    drawlayout.closeDrawers()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, ProfileFragment())

                            .commit()
                    supportActionBar?.title="My Profile"
                    drawlayout.closeDrawers()
                }
                R.id.about -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, AboutAppFragment())

                            .commit()
                    supportActionBar?.title="About App"
                    drawlayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }



    }
    fun setUpToolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title="toolbar title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home)
        {
            drawlayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
    fun openDashboard(){
        val fragment=DashboardFragment()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
        supportActionBar?.title="Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.frame_layout)
        when(frag)
        {
            !is DashboardFragment->openDashboard()
            else->super.onBackPressed()
        }
    }
}