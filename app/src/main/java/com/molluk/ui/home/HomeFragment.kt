package com.molluk.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.molluk.databinding.FragmentHomeBinding
import com.molluk.ui.base.BaseFragment
import com.molluk.ui.home.categories.Categories
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        initViews()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        with(binding) {
            character.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCategoriesFragment(Categories.Character.name)
                saveNavigate(action)
            }
            location.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCategoriesFragment(Categories.Location.name)
                saveNavigate(action)
            }
            episode.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCategoriesFragment(Categories.Episode.name)
                saveNavigate(action)
            }
        }
    }
}