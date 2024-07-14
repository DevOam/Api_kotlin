package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.services.ApiService
import com.example.myapplication.services.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity() {

    private lateinit var usernameTextView: TextView
    private lateinit var followersCountTextView: TextView
    private lateinit var followingCountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize TextViews
        usernameTextView = findViewById(R.id.username)
        followersCountTextView = findViewById(R.id.followersCount)
        followingCountTextView = findViewById(R.id.followingCount)

    }

    private fun fetchProfileData() {
        // Create a Retrofit instance
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://your-api-base-url.com") // Replace with your API base URL
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Replace "guest" with the actual username you want to query
        val username = "guest"

        // Fetch user details
        apiService.getFollow(username).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    user?.let {
                        usernameTextView.text = it.username
                        followersCountTextView.text = it.follow.toString()
                        followingCountTextView.text = it.following.toString()
                    }
                } else {
                    Log.e("ProfileActivity", "Failed to fetch user details: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("ProfileActivity", "Error fetching user details", t)
            }
            })
        }
}
