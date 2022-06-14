package com.example.projectgithubuser_wildanfajrialfarabi

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectgithubuser_wildanfajrialfarabi.databinding.ActivityFavouriteBinding

class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var recviewFav: RecyclerView
    private lateinit var favouriteAddViewModel: FavouriteAddViewModel

    companion object {
        private const val TITLE = "Favourite"
        private const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = TITLE

        recviewFav = binding.recviewFav
        recviewFav.setHasFixedSize(true)

        val factory = ViewModelFactory.getInstance(this.application)
        favouriteAddViewModel = ViewModelProvider(
            this, factory
        )[FavouriteAddViewModel::class.java]

        favouriteAddViewModel.getAllFav().observe(this) { favList ->
            favList.let {
                if (it.isEmpty()) {
                    binding.tidakAdaFavourite.visibility = View.VISIBLE
                    binding.recviewFav.visibility = View.GONE
                }
                else setFavData(it)
            }
        }
    }

    private fun setFavData(user: List<Favourite>) {
        binding.tidakAdaFavourite.visibility = View.GONE
        binding.recviewFav.visibility = View.VISIBLE
        recviewFav.layoutManager = LinearLayoutManager(this)
        val adapter = FavouriteAdapter(user)
        binding.recviewFav.adapter = adapter
        adapter.setOnItemClickCallback(object: FavouriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Favourite) {
                val detailed = data.username
                val intentToDetail = Intent(this@FavouriteActivity, UserDetailActivity::class.java)
                intentToDetail.putExtra(EXTRA_USERNAME, detailed)
                startActivity(intentToDetail)
            }

            override fun onFavButtonClicker(data: Favourite) {
                favouriteAddViewModel.delete(data)
            }
        })
    }


}