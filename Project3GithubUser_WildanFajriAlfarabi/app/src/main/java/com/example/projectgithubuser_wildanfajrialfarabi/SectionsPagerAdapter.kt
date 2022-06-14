package com.example.projectgithubuser_wildanfajrialfarabi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity):FragmentStateAdapter(activity) {
    private lateinit var uName: String

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var whatfragment: Fragment? = null
        when (position) {

            0 -> {
                whatfragment = FollowersFragment()
                val mBundle = Bundle()
                mBundle.putString(EXTRA_USERNAME,uName)
                whatfragment.arguments = mBundle
            }
            1 -> {
                whatfragment = FollowingFragment()
                val mBundle = Bundle()
                mBundle.putString(EXTRA_USERNAME,uName)
                whatfragment.arguments = mBundle
            }
        }
        return whatfragment as Fragment
    }

    fun setUname(username: String){
        uName = username
    }
}