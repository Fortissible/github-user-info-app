package com.example.projectgithubuser_wildanfajrialfarabi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectgithubuser_wildanfajrialfarabi.databinding.FollowersItemBinding
import com.bumptech.glide.Glide

class FollowersInfoAdapter (private val listFollow: List<FollowResponseItem>): RecyclerView.Adapter<FollowersInfoAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = FollowersItemBinding.bind(itemView)
        var profilePic: ImageView = binding.followersuserProfilepic
        var username: TextView = binding.followersuserUsername
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.followers_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listFollow.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val profilepic = listFollow[position].avatarUrl
        val username = listFollow[position].login
        Glide.with(holder.itemView)
            .load(profilepic)
            .into(holder.profilePic)
        holder.username.text = username
    }
}