package com.example.projectgithubuser_wildanfajrialfarabi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectgithubuser_wildanfajrialfarabi.databinding.FollowingItemBinding
import com.bumptech.glide.Glide

class FollowingInfoAdapter(private val listFollow: List<FollowResponseItem>):RecyclerView.Adapter<FollowingInfoAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = FollowingItemBinding.bind(itemView)
        var profilePic: ImageView = binding.followinguserProfilepic
        var username: TextView = binding.followinguserUsername
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.following_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val profilepic = listFollow[position].avatarUrl
        val username = listFollow[position].login
        Glide.with(holder.itemView)
            .load(profilepic)
            .into(holder.profilePic)
        holder.username.text = username
    }

    override fun getItemCount(): Int = listFollow.size
}