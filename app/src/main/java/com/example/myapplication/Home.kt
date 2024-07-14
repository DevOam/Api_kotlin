package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.AdaptePosts
import com.example.myapplication.services.ApiService
import com.example.myapplication.services.Postss
import com.google.android.material.navigation.NavigationView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    lateinit var token: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        getToken()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchPosts(recyclerView)
    }

    private fun getToken() {
        token = intent.getStringExtra("token").toString()
        if (token.isEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: finish the current activity
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the home action
            }
            R.id.nav_profile -> {
                // Handle the profile action
            }
        }
        drawerLayout.closeDrawer(navView)
        return true
    }

    private fun fetchPosts(recyclerView: RecyclerView) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://yourapiurl.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getAllPosts().enqueue(object : Callback<List<Postss>> {
            override fun onResponse(call: Call<List<Postss>>, response: Response<List<Postss>>) {
                if (response.isSuccessful) {
                    val posts = response.body() ?: listOf()
                    recyclerView.adapter = AdaptePosts(posts)
                } else {
                    Toast.makeText(this@Home, "Failed to fetch posts", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Postss>>, t: Throwable) {
                Toast.makeText(this@Home, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
