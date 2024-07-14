package com.example.myapplication.services

import com.example.myapplication.Adapter.AdaptePosts
import com.example.myapplication.model.Post
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String, val user: User)
data class User(val id: Int, val username: String, val follow: Int, val following: Int)
data class Postss(val likes: Int, val userPost: AdaptePosts, val userfollowers: Int, val postsOfFollowers: AdaptePosts)

interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @GET("username")
    fun getUsername(): Call<User>

    @GET("user/follow")
    fun getFollow(@Query("username") username: String): Call<User>

    @GET("post/like")
    fun getLikesPost(@Query("id") postId: String): Call<List<Postss>>

    @GET("post/user-posts")
    fun getSelfUserPost(): Call<List<Postss>>

    @GET("post")
    fun getAllPosts(): Call<List<Postss>>

    @GET("post/followed-users-posts")
    fun getFollowers(): Call<List<Postss>>
}
