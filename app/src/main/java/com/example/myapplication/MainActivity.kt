package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


import android.widget.Toast
import com.example.myapplication.model.Post
import com.example.myapplication.services.LoginRequest
import com.example.myapplication.services.LoginResponse
import com.example.myapplication.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.myapplication.services.ApiService
import com.example.myapplication.services.TestUser


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test()
        // Get references to the views
        val emailInputLayout: TextInputLayout = findViewById(R.id.email)
        val passwordInputLayout: TextInputLayout = findViewById(R.id.password)
        val emailEditText: TextInputEditText = findViewById(R.id.emailEditText)
        val passwordEditText: TextInputEditText = findViewById(R.id.passwordEditText)
        val loginButton: TextView = findViewById(R.id.loginButton)

        // Set up a click listener for the login button
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val testEmail = "test@test.com"
            val testPassword = "123456"
            if(testEmail == email && testPassword == password){
                var intent = Intent(this,Home::class.java)
                intent.putExtra("token","tokenId")
                startActivity(intent)
            } else if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                if (email.isEmpty()) {
                    emailInputLayout.error = "Email is required"
                }
                if (password.isEmpty()) {
                    passwordInputLayout.error = "Password is required"
                }
            }
        }
    }


    private fun test(){
        val apiService = TestUser.apiService
        val call = apiService.getPosts()

        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    posts?.forEach {
                        Log.d("Post", "Title: ${it.title}, Body: ${it.body}")
                    }
                } else {
                    Log.e("API call", "Failed to fetch data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("API call", "Failed to fetch data: ${t.message}")
            }
    })
    }


    private fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        val call = RetrofitClient.apiService.login(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        // Login successful
                        showMessage("Login successful! Welcome ${it.token}")
                        if(loginRequest.email == email && loginRequest.password == password){
                            var intent = Intent(this@MainActivity,Home::class.java)
                            intent.putExtra("token",loginResponse.token)
                            startActivity(intent)
                        }
                        // Perform necessary actions such as saving token, navigating to another screen, etc.
                    }
                } else {
                    // Login failed
                    showMessage("Invalid email or password")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle failure
                showMessage("Login failed: ${t.message}")
            }
        })
    }

    // Function to show a simple message
    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
