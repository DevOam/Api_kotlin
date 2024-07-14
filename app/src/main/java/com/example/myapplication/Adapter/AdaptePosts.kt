package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.services.Postss
import com.squareup.picasso.Picasso


class AdaptePosts(private val posts: List<Postss>) : RecyclerView.Adapter<AdaptePosts.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val username: TextView = itemView.findViewById(R.id.username)
//        val timestamp: TextView = itemView.findViewById(R.id.timestamp)
//        val content: TextView = itemView.findViewById(R.id.content)
        val postImage: ImageView = itemView.findViewById(R.id.post_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
//        holder.username.text = post.username
//        holder.timestamp.text = post.timestamp
//        holder.content.text = post.content
    }

    override fun getItemCount() = posts.size
}
