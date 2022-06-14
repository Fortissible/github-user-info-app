package com.example.projectgithubuser_wildanfajrialfarabi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
class FavouriteAdapter(private val fav: List<Favourite>): RecyclerView.Adapter<FavouriteAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var profilePic: ImageView = itemView.findViewById(R.id.user_profilepic)
        var username: TextView = itemView.findViewById(R.id.user_username)
        val favButton: ImageButton = itemView.findViewById(R.id.button_loved_recview)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val buttonFav = holder.favButton
        val profilepic = fav[position].profilepic
        val username = fav[position].username
        buttonFav.visibility = View.VISIBLE
        buttonFav.setOnClickListener {
            onItemClickCallback.onFavButtonClicker(fav[holder.adapterPosition])
        }
        Glide.with(holder.itemView)
            .load(profilepic)
            .into(holder.profilePic)
        holder.username.text = username
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(fav[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = fav.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Favourite)
        fun onFavButtonClicker(data: Favourite)
    }
}