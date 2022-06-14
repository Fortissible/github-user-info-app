package com.example.projectgithubuser_wildanfajrialfarabi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectgithubuser_wildanfajrialfarabi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var recviewUserGithub: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    companion object {
        private const val EXTRA_USERNAME = "extra_username"
        private const val TITLE = "Search User"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = TITLE

        recviewUserGithub = binding.githubUserlist
        recviewUserGithub.setHasFixedSize(true)

        val factory = ViewModelFactory.getInstance(this.application)
        mainViewModel = ViewModelProvider(
            this, factory
        )[MainViewModel::class.java]

        mainViewModel.listUser.observe(this) { user ->
            setUserData(user)
            if (user.isEmpty()) {
                binding.initialText.visibility = View.VISIBLE
                binding.githubUserlist.visibility = View.GONE
                Log.d("User empty", "111111")
            }
            else {
                binding.initialText.visibility = View.GONE
                binding.githubUserlist.visibility = View.VISIBLE
                Log.d("User ada", "111111")
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.FavFAB.setOnClickListener {
            val intentToFavourite = Intent(this, FavouriteActivity::class.java)
            startActivity(intentToFavourite)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String): Boolean {
                binding.initialText.visibility = View.GONE
                binding.githubUserlist.visibility = View.GONE
                mainViewModel.searchUser(p0,this@MainActivity)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        val pref = OptionPreferences.getInstance(applicationContext.dataStore)
        val preferencesViewModel = ViewModelProvider(
            this, PreferenceViewModelFactory(pref)
        )[PreferencesViewModel::class.java]
        preferencesViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_option -> {
                val fragment = supportFragmentManager.findFragmentByTag(OptionFragment::class.java.simpleName)
                if (fragment !is OptionFragment) {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_activity, OptionFragment(), OptionFragment::class.java.simpleName)
                        .addToBackStack(null)
                        .commit()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUserData(user: List<ItemsItem>) {
        recviewUserGithub.layoutManager = LinearLayoutManager(this)
        val adapter = ListUserAdapter(user)
        binding.githubUserlist.adapter = adapter
        adapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val detailed = data.login
                val intentToDetail = Intent(this@MainActivity, UserDetailActivity::class.java)
                intentToDetail.putExtra(EXTRA_USERNAME, detailed)
                startActivity(intentToDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
