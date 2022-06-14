package com.example.projectgithubuser_wildanfajrialfarabi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectgithubuser_wildanfajrialfarabi.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {
    private lateinit var recviewFollowing: RecyclerView
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var uname: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentFollowingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            uname = arguments?.getString(SectionsPagerAdapter.EXTRA_USERNAME)?:""
        }

        recviewFollowing = view.findViewById(R.id.rvgithub_following)
        recviewFollowing.setHasFixedSize(true)

        val followingViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[FollowingViewModel::class.java]

        if (followingViewModel.flag.value == 0) followingViewModel.searchFollowing(uname,requireActivity())

        followingViewModel.listFollowing.observe(viewLifecycleOwner){ following->
            if (following.isEmpty()) binding.followingInitialText.visibility = View.VISIBLE
            setFollowing(followingViewModel.listFollowing.value!!)
        }

        followingViewModel.isLoadingFollowing.observe(viewLifecycleOwner){ following->
            showLoading(following)
        }
    }

    private fun setFollowing(following: List<FollowResponseItem>) {
        recviewFollowing.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = FollowingInfoAdapter(following)
        recviewFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.followingProgressBar.visibility = View.VISIBLE
        binding.followingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}