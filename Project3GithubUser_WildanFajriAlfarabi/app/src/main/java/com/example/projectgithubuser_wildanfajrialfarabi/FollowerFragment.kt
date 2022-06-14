package com.example.projectgithubuser_wildanfajrialfarabi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectgithubuser_wildanfajrialfarabi.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {
    private lateinit var recviewFollower: RecyclerView
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var uname: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentFollowersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            uname = arguments?.getString(SectionsPagerAdapter.EXTRA_USERNAME)?:""
        }

        recviewFollower = view.findViewById(R.id.rvgithub_followers)
        recviewFollower.setHasFixedSize(true)

        val followersViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[FollowersViewModel::class.java]

        if (followersViewModel.flag.value == 0) followersViewModel.searchFollowers(uname, requireActivity())

        followersViewModel.listFollower.observe(viewLifecycleOwner){ follower->
            if (follower.isEmpty()) binding.followerInitialText.visibility = View.VISIBLE
            setFollower(followersViewModel.listFollower.value!!)
        }

        followersViewModel.isLoadingFollowers.observe(viewLifecycleOwner){ follower->
            showLoading(follower)
        }

    }

    private fun setFollower(follower: List<FollowResponseItem>) {
        recviewFollower.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = FollowersInfoAdapter(follower)
        recviewFollower.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.followersProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}