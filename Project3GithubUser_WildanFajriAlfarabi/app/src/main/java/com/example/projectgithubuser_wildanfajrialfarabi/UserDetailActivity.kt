package com.example.projectgithubuser_wildanfajrialfarabi

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.projectgithubuser_wildanfajrialfarabi.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import de.hdodenhof.circleimageview.CircleImageView

class UserDetailActivity: AppCompatActivity() {

    private lateinit var detailViewModel: MainViewModel
    private lateinit var favouriteAddViewModel : FavouriteAddViewModel
    private lateinit var binding: ActivityUserDetailBinding
    private var favourite: Favourite? = null
    private var isFavourited = false
    private lateinit var listFav : List<Favourite>

    companion object {
        private val TAB_TITLES = listOf("Followers","Following")
        private const val EXTRA_USERNAME = "extra_username"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        supportActionBar?.elevation = 0f

        val factory = ViewModelFactory.getInstance(this.application)
        detailViewModel = ViewModelProvider(
            this, factory
        )[MainViewModel::class.java]

        favouriteAddViewModel = ViewModelProvider(
            this, factory
        )[FavouriteAddViewModel::class.java]

        val user = intent.getStringExtra(EXTRA_USERNAME)
        
        if (detailViewModel.flag.value == 0) detailViewModel.searchDetail(user.toString(),this)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        sectionsPagerAdapter.setUname(user.toString())

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs:TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager){ tab,pos ->
            tab.text = TAB_TITLES[pos]
        }.attach()

        detailViewModel.detailIsLoading.observe(this){
            showLoading(it)
        }

        favouriteAddViewModel.getAllFav().observe(this) { favList ->
            listFav = favList
            detailViewModel.listDetail.observe(this){
                setDetailData(detailViewModel.listDetail.value)
                setFavourite(detailViewModel.listDetail.value)
                checkId(listFav)
            }
        }

        binding.favButton.setOnClickListener{
            val bindFavButton = binding.favButton
            if (!isFavourited) {
                bindFavButton.setImageDrawable(ContextCompat.getDrawable(bindFavButton.context, R.drawable.ic_favourited))
                isFavourited = true
                favouriteAddViewModel.insert(favourite as Favourite)
            } else {
                bindFavButton.setImageDrawable(ContextCompat.getDrawable(bindFavButton.context, R.drawable.ic_favourite))
                isFavourited = false
                favouriteAddViewModel.delete(favourite as Favourite)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailData(userDetail: UserDetailsResponse?){
        val nama:TextView = binding.userdetailName
        val username:TextView = binding.userdetailUsername
        val profilepic:CircleImageView = binding.userdetailProfilepic
        val following:TextView = binding.userdetailFollowing
        val followers:TextView = binding.userdetailFollowers
        val company:TextView = binding.userdetailCompany
        val repository:TextView = binding.userdetailRepository
        val location:TextView = binding.userdetailLocation

        nama.text = userDetail?.name ?: "[Name not set]"
        username.text = userDetail?.login
        location.text = userDetail?.location ?: "[Location not set]"
        followers.text = "Followers : ${userDetail?.followers.toString()}"
        following.text = "Following : ${userDetail?.following.toString()}"
        company.text = userDetail?.company ?: "[Company not set]"
        repository.text = "This user have ${userDetail?.publicRepos.toString()} repository"
        Glide.with(this@UserDetailActivity)
            .load(userDetail?.avatarUrl)
            .override(500, 500)
            .fitCenter()
            .into(profilepic)
    }

    private fun showLoading(isLoading: Boolean) {
        val detailProgressBar = binding.detailProgressBar
        val detailContent = binding.detailusercard
        detailProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        detailContent.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    private fun setFavourite(userDetail: UserDetailsResponse?){
        favourite = Favourite()
        favourite?.nama = userDetail?.name
        favourite?.company = userDetail?.company
        favourite?.username = userDetail?.login
        favourite?.profilepic = userDetail?.avatarUrl
        favourite?.followers = userDetail?.followers.toString()
        favourite?.following = userDetail?.following.toString()
        favourite?.repository = userDetail?.publicRepos.toString()
        favourite?.location = userDetail?.location
    }

    private fun checkId(idList: List<Favourite>) {
        val bindFavButton = binding.favButton
        for (i in idList){
            if (i.username == favourite?.username) {
                favourite?.id = i.id
                isFavourited = true
                bindFavButton.setImageDrawable(ContextCompat.getDrawable(bindFavButton.context, R.drawable.ic_favourited))
            }
        }
    }
}